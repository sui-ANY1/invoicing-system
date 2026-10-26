package com.ruoyi.framework.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.DemoModeException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.html.EscapeUtil;

/**
 * 全局异常处理器
 * 
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return AjaxResult.error(HttpStatus.BAD_METHOD, e.getMessage());
    }

    /**
     * 业务异常
     *
     * <p>REST 改造后：业务异常的 code 会同步成 HTTP 状态码（由 HttpStatusFilter 统一落地）。
     * 未显式指定 code 的，按提示语推断语义：不存在→404、参数类→400、状态冲突→409、其余→500。</p>
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage())
                : AjaxResult.error(inferStatus(e.getMessage()), e.getMessage());
    }

    /**
     * 按业务提示语推断 HTTP 状态码（无显式 code 时的兜底口径）
     */
    private static int inferStatus(String message)
    {
        if (StringUtils.isEmpty(message))
        {
            return HttpStatus.ERROR;
        }
        if (message.contains("不存在") || message.contains("已被清理"))
        {
            return HttpStatus.NOT_FOUND;
        }
        if (message.contains("无权") || message.contains("不属于当前"))
        {
            return HttpStatus.FORBIDDEN;
        }
        if (message.contains("参数缺失") || message.contains("不合法") || message.contains("格式不正确")
                || message.contains("必填") || message.contains("必须是平台内")
                || message.contains("不能为空") || message.contains("请填写") || message.contains("请选择")
                || message.contains("请先上传") || message.contains("至少") || message.contains("合计必须")
                || message.contains("仅支持") || message.contains("只对") || message.contains("不支持的"))
        {
            return HttpStatus.BAD_REQUEST;
        }
        if (message.contains("不允许") || message.contains("不能") || message.contains("无法")
                || message.contains("仍在有效期内"))
        {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.ERROR;
    }

    /**
     * 请求路径中缺少必需的路径变量
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public AjaxResult handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求路径中缺少必需的路径变量'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, String.format("请求路径中缺少必需的路径变量[%s]", e.getVariableName()));
    }

    /**
     * 请求参数类型不匹配
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        String value = Convert.toStr(e.getValue());
        if (StringUtils.isNotEmpty(value))
        {
            value = EscapeUtil.clean(value);
        }
        log.error("请求参数类型不匹配'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, String.format("请求参数类型不匹配，参数[%s]要求类型为：'%s'，但输入值为：'%s'", e.getName(), e.getRequiredType().getName(), value));
    }

    /**
     * 接口不存在（REST 化后已删除的路径或拼错的路径）：返回 404，不再笼统报 500
     */
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public AjaxResult handleNoResourceFound(
            org.springframework.web.servlet.resource.NoResourceFoundException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.warn("请求地址'{}',接口不存在.", requestURI);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "接口不存在：" + requestURI);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        // 业务里常直接用 RuntimeException 抛出「主体不存在」这类提示，按文案归一到 400/404/409
        return AjaxResult.error(inferStatus(e.getMessage()), e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error(HttpStatus.FORBIDDEN, "演示模式，不允许操作");
    }

    /**
     * 缺少 multipart 文件部分（如上传接口未带 file）→ 400
     */
    @ExceptionHandler(org.springframework.web.multipart.support.MissingServletRequestPartException.class)
    public AjaxResult handleMissingServletRequestPart(
            org.springframework.web.multipart.support.MissingServletRequestPartException e)
    {
        log.error("缺少上传文件部分'{}'", e.getRequestPartName());
        return AjaxResult.error(HttpStatus.BAD_REQUEST, "缺少上传内容：" + e.getRequestPartName());
    }

    /**
     * 请求 Content-Type 不受支持（如上传接口未按 multipart/form-data 提交）→ 415
     */
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    public AjaxResult handleHttpMediaTypeNotSupported(
            org.springframework.web.HttpMediaTypeNotSupportedException e)
    {
        log.error("Content-Type 不支持：{}", e.getContentType());
        return AjaxResult.error(HttpStatus.UNSUPPORTED_TYPE,
                "请求的 Content-Type 不受支持：" + e.getContentType() + "；上传接口请使用 multipart/form-data");
    }
}
