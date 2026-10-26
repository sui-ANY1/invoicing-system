package com.ruoyi.web.advice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * REST 改造配套：兜底把 JSON 响应体里的 code 同步到 HTTP 状态码。
 *
 * <p>为什么需要它：{@link HttpStatusAdvice} 能覆盖控制器正常返回与 @ExceptionHandler 之外的部分场景，
 * 但经实测，Spring Security 的 401 入口与 {@code @ExceptionHandler} 返回的 AjaxResult 不经过
 * ResponseBodyAdvice，仍然是 HTTP 200 + body code。这里在过滤器层再兜一层，
 * 覆盖全部出口（拦截器、异常处理器、Security 入口）。</p>
 *
 * <p>规则：仅当 HTTP 状态仍为 200、内容类型为 JSON、body 里出现 "code":4xx/5xx 时改写状态，
 * 控制器显式声明的 201/204 与文件流不受影响。</p>
 *
 * @author sui-ANY1
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpStatusFilter implements Filter
{
    private static final Pattern CODE = Pattern.compile("\"code\"\\s*:\\s*(\\d{3})");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        if (!(response instanceof HttpServletResponse httpResponse))
        {
            chain.doFilter(request, response);
            return;
        }
        ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(httpResponse);
        chain.doFilter(request, wrapper);
        try
        {
            alignStatus(wrapper);
        }
        finally
        {
            wrapper.copyBodyToResponse();
        }
    }

    private void alignStatus(ContentCachingResponseWrapper wrapper)
    {
        if (wrapper.getStatus() != HttpServletResponse.SC_OK)
        {
            return;
        }
        byte[] body = wrapper.getContentAsByteArray();
        if (body.length == 0)
        {
            return;
        }
        String contentType = wrapper.getContentType();
        if (contentType == null || !contentType.toLowerCase().contains("json"))
        {
            return;
        }
        String text = new String(body, StandardCharsets.UTF_8);
        if (text.length() > 2000)
        {
            text = text.substring(0, 2000);
        }
        Matcher matcher = CODE.matcher(text);
        if (matcher.find())
        {
            int code = Integer.parseInt(matcher.group(1));
            if (code >= 400 && code <= 599)
            {
                wrapper.setStatus(code);
            }
        }
    }
}
