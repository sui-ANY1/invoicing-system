package com.ruoyi.web.advice;

import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * REST 改造配套：让 HTTP 状态码与响应体里的 code 一致。
 *
 * 背景：RuoYi 默认所有错误都用 HTTP 200 + body {code:401/500} 表达，
 * 不符合 REST 语义。这里在响应写出前读 body 的 code，同步到 HTTP 状态：
 *   400 参数错误 / 401 未登录 / 403 无权限 / 404 不存在 / 409 状态冲突 / 500 服务异常 / 501 功能未开通
 *
 * 两条保护：
 * 1) 控制器显式声明的状态（如 @ResponseStatus(CREATED) = 201、NO_CONTENT = 204）优先，不覆盖；
 * 2) 文件流、字符串等没有 code 的响应原样放行。
 *
 * @author sui-ANY1
 */
@RestControllerAdvice
public class HttpStatusAdvice implements ResponseBodyAdvice<Object>
{
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType)
    {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response)
    {
        if (body == null)
        {
            return null;
        }
        // 控制器已经显式指定过状态（201/204 等），不覆盖
        if (response instanceof ServletServerHttpResponse servletResponse
                && servletResponse.getServletResponse().getStatus() != HttpStatus.OK.value())
        {
            return body;
        }
        Integer code = readCode(body);
        if (code == null || code < 400 || code > 599)
        {
            return body;
        }
        try
        {
            response.setStatusCode(HttpStatus.valueOf(code));
        }
        catch (IllegalArgumentException ignore)
        {
            // 非标准状态码（如 1000 这类业务码），保持 HTTP 200
        }
        return body;
    }

    /**
     * 反射读取 body 的 getCode()，兼容 R / PcResult / AjaxResult 三种信封。
     */
    private Integer readCode(Object body)
    {
        try
        {
            Method method = body.getClass().getMethod("getCode");
            Object value = method.invoke(body);
            return value instanceof Integer ? (Integer) value : null;
        }
        catch (Exception ignore)
        {
            return null;
        }
    }
}
