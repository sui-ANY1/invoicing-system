package com.ruoyi.system.mini.interceptor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.mini.security.MiniLoginUser;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.security.MiniTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 小程序接口鉴权拦截器
 *
 * @author sui-ANY1
 */
@Component
public class MiniAuthInterceptor implements HandlerInterceptor {
    /**
     * 免 token 白名单（登录、固定码注册提交、材料上传）
     *
     * <p>白名单接口为“可选鉴权”：未携带 token 时放行；携带有效 token 时仍解析并建立登录上下文，
     * 便于注册提交等场景把收件单归属到当前会员（匿名提交仍然允许，此时归属为空）。</p>
     */
    private static final Set<String> WHITE_URLS = Set.of(
            "POST /api/mini/sessions",
            "POST /api/mini/registration-submissions",
            "POST /api/mini/files"
    );

    @Autowired
    private MiniTokenService miniTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        // 白名单按「方法 + 路径」精确匹配：同一路径的 GET（查历史）仍需登录，POST（匿名提交）放行
        boolean white = WHITE_URLS.contains(request.getMethod() + " " + uri);
        if (white) {
            MiniLoginUser whiteUser = miniTokenService.getLoginUser(request);
            if (whiteUser != null) {
                miniTokenService.verifyToken(whiteUser);
                MiniSecurityUtils.set(whiteUser);
            }
            return true;
        }

        MiniLoginUser loginUser = miniTokenService.getLoginUser(request);
        if (loginUser == null) {
            renderUnauthorized(response);
            return false;
        }

        // 自动续期，并把当前会员放入上下文
        miniTokenService.verifyToken(loginUser);
        MiniSecurityUtils.set(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MiniSecurityUtils.clear();
    }

    /**
     * 返回 401：HTTP 状态与 body 的 code 一致（REST 语义），前端按状态码或 code 判断均可
     */
    private void renderUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(
                AjaxResult.error(401, "登录状态已过期，请重新登录")));
    }
}
