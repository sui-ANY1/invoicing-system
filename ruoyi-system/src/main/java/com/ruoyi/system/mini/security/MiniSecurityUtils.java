package com.ruoyi.system.mini.security;

/**
 * 小程序登录用户上下文（ThreadLocal，请求结束由拦截器清理）
 *
 * @author sui-ANY1
 */
public class MiniSecurityUtils {
    private static final ThreadLocal<MiniLoginUser> CONTEXT = new ThreadLocal<>();

    public static void set(MiniLoginUser user) {
        CONTEXT.set(user);
    }

    public static MiniLoginUser get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    /**
     * 获取当前登录会员 id，未登录直接抛异常（拦截器已保证登录，正常不会触发）
     */
    public static Long getMemberId() {
        MiniLoginUser user = CONTEXT.get();
        if (user == null || user.getMemberId() == null) {
            throw new RuntimeException("未获取到小程序登录用户，请先登录");
        }
        return user.getMemberId();
    }

    /**
     * 获取当前登录会员 id，未登录或未绑定会员时返回 null（用于“可选鉴权”的白名单接口）
     */
    public static Long getMemberIdOrNull() {
        MiniLoginUser user = CONTEXT.get();
        return user == null ? null : user.getMemberId();
    }
}
