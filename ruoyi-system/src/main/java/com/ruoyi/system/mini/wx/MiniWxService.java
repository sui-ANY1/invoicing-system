package com.ruoyi.system.mini.wx;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;

/**
 * 微信小程序接口对接（未配置 AppID 时是否允许模拟登录由 mini.wx.mock-enabled 显式控制，默认允许以便联调）
 *
 * @author sui-ANY1
 */
@Component
public class MiniWxService {
    private static final Logger log = LoggerFactory.getLogger(MiniWxService.class);

    /**
     * access_token 在 Redis 的缓存 key
     */
    private static final String ACCESS_TOKEN_KEY = "wx:access_token";

    /**
     * access_token 缓存秒数（微信有效期 7200，提前一点过期）
     */
    private static final int ACCESS_TOKEN_TTL = 7000;

    @Value("${mini.wx.app-id:}")
    private String appId;

    @Value("${mini.wx.app-secret:}")
    private String appSecret;

    /**
     * 未配置微信凭证时是否允许模拟登录（生产必须为 false 并配置真实凭证）
     */
    @Value("${mini.wx.mock-enabled:true}")
    private boolean mockEnabled;

    @Autowired
    private RedisCache redisCache;

    /**
     * 是否为模拟模式（未配置凭证）
     */
    public boolean isMock() {
        return StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret);
    }

    /**
     * 是否允许模拟登录：仅在「未配置凭证」且「显式开启模拟」时为真
     */
    public boolean isMockLoginAllowed() {
        return isMock() && mockEnabled;
    }

    @jakarta.annotation.PostConstruct
    public void checkWxConfig() {
        if (isMock() && mockEnabled) {
            log.warn("未配置 mini.wx.app-id/mini.wx.app-secret，小程序登录处于模拟模式（任意 code 均可换取登录态）："
                    + "仅限本地联调，生产环境必须配置微信凭证并将 MINI_WX_MOCK_ENABLED 设为 false");
        } else if (isMock()) {
            log.warn("未配置微信凭证且模拟登录已关闭（mini.wx.mock-enabled=false）：小程序登录将不可用");
        }
    }

    /**
     * 用 wx.login 的 code 换取 openid
     * 未配置凭证且允许模拟时，以 "mock_" + code 作为模拟 openid（同 code 同 openid）
     */
    public String codeToOpenid(String code) {
        if (isMock()) {
            if (!mockEnabled) {
                throw new ServiceException("微信凭证未配置且模拟登录已关闭，无法完成登录");
            }
            return "mock_" + code;
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        String resp = HttpUtils.sendGet(url);
        JSONObject json = JSON.parseObject(resp);
        String openid = json.getString("openid");
        if (StringUtils.isEmpty(openid)) {
            throw new RuntimeException("微信登录失败：" + json.getString("errmsg"));
        }
        return openid;
    }

    /**
     * 用 getPhoneNumber 的 code 换取手机号（真实模式）
     */
    public String getPhoneByCode(String phoneCode) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        String resp = HttpUtils.sendPost(url, "{\"code\":\"" + phoneCode + "\"}", "application/json");
        JSONObject json = JSON.parseObject(resp);
        JSONObject phoneInfo = json.getJSONObject("phone_info");
        if (phoneInfo == null) {
            throw new RuntimeException("获取手机号失败：" + json.getString("errmsg"));
        }
        return phoneInfo.getString("phoneNumber");
    }

    /**
     * 获取微信接口调用凭据 access_token（带 Redis 缓存）
     */
    public String getAccessToken() {
        String cached = redisCache.getCacheObject(ACCESS_TOKEN_KEY);
        if (StringUtils.isNotEmpty(cached)) {
            return cached;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token"
                + "?grant_type=client_credential"
                + "&appid=" + appId
                + "&secret=" + appSecret;
        String resp = HttpUtils.sendGet(url);
        JSONObject json = JSON.parseObject(resp);
        String accessToken = json.getString("access_token");
        if (StringUtils.isEmpty(accessToken)) {
            throw new RuntimeException("获取 access_token 失败：" + json.getString("errmsg"));
        }
        redisCache.setCacheObject(ACCESS_TOKEN_KEY, accessToken, ACCESS_TOKEN_TTL, TimeUnit.SECONDS);
        return accessToken;
    }
}
