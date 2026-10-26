package com.ruoyi.system.mini.security;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 小程序 token 签发与校验（独立 Redis 前缀，与 PC 端隔离）
 *
 * @author sui-ANY1
 */
@Component
public class MiniTokenService {
    private static final Logger log = LoggerFactory.getLogger(MiniTokenService.class);

    /**
     * 小程序登录身份在 Redis 的 key 前缀
     */
    private static final String MINI_TOKEN_KEY = "mini_tokens:";

    /**
     * JWT 中存放会话标识的字段名
     */
    private static final String MINI_LOGIN_USER_KEY = "mini_login_user_key";

    protected static final long MILLIS_SECOND = 1000L;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    protected static final long MILLIS_DAY = 24 * 60 * MILLIS_MINUTE;

    /**
     * 剩余有效期不足 1 天，自动续期
     */
    private static final long REFRESH_THRESHOLD = MILLIS_DAY;

    /**
     * 令牌自定义标识（复用 PC 配置 Authorization）
     */
    @Value("${token.header}")
    private String header;

    /**
     * 小程序令牌密钥：优先 mini.token.secret（环境变量 MINI_TOKEN_SECRET），未配置时回退 token.secret。
     * <p>注意：application.yml 中 mini.token.secret 写成 {@code ${MINI_TOKEN_SECRET:}}，属于「存在但为空」的占位符，
     * 因此不能依赖 {@code @Value} 的默认值语法回退，必须显式判断空值。</p>
     */
    @Value("${mini.token.secret:}")
    private String miniSecret;

    /**
     * 后台令牌密钥，作为小程序未单独配置时的回退
     */
    @Value("${token.secret:}")
    private String fallbackSecret;

    /**
     * 实际参与签名的密钥（由 {@link #checkTokenSecret()} 计算）
     */
    private String secret;

    /**
     * 内置开发令牌密钥（仅本地联调，生产必须通过 TOKEN_SECRET 覆盖）
     */
    private static final String DEV_SECRET = "abcdefghijklmnopqrstuvwxyz";

    @jakarta.annotation.PostConstruct
    public void checkTokenSecret() {
        if (StringUtils.isEmpty(miniSecret)) {
            log.warn("未单独配置 mini.token.secret，小程序令牌回退使用 token.secret 签名："
                    + "生产环境请通过环境变量 MINI_TOKEN_SECRET 为小程序分设独立密钥，避免与后台令牌同钥");
            secret = fallbackSecret;
        } else {
            secret = miniSecret;
        }
        if (StringUtils.isEmpty(secret)) {
            throw new IllegalStateException("token.secret 未配置，无法签发令牌");
        }
        if (DEV_SECRET.equals(secret)) {
            log.warn("token.secret 仍是内置开发密钥，令牌存在被伪造风险：生产环境必须通过环境变量 TOKEN_SECRET "
                    + "配置至少 32 位随机密钥（小程序侧另配 MINI_TOKEN_SECRET）");
        }
    }

    /**
     * 小程序登录态有效期（天），默认 7 天，可在 application.yml 用 mini.token.expire-days 覆盖
     */
    @Value("${mini.token.expire-days:7}")
    private int expireDays;

    @Autowired
    private RedisCache redisCache;

    /**
     * 创建令牌
     */
    public String createToken(MiniLoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(MINI_LOGIN_USER_KEY, token);
        // subject 放手机号，没有手机号时放 openid
        String subject = StringUtils.isNotEmpty(loginUser.getPhone())
                ? loginUser.getPhone() : loginUser.getOpenid();
        claims.put(Constants.JWT_USERNAME, subject);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从请求中解析登录用户，无效或已过期返回 null
     */
    public MiniLoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(MINI_LOGIN_USER_KEY);
                return redisCache.getCacheObject(MINI_TOKEN_KEY + uuid);
            } catch (Exception e) {
                log.error("获取小程序登录用户异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    /**
     * 校验令牌，剩余有效期不足阈值则自动续期
     */
    public void verifyToken(MiniLoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= REFRESH_THRESHOLD) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     */
    public void refreshToken(MiniLoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireDays * MILLIS_DAY);
        String userKey = MINI_TOKEN_KEY + loginUser.getToken();
        redisCache.setCacheObject(userKey, loginUser, expireDays, TimeUnit.DAYS);
    }

    /**
     * 删除登录身份（注销）
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisCache.deleteObject(MINI_TOKEN_KEY + token);
        }
    }

    /**
     * 从请求头获取 token（去掉 Bearer 前缀）
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
