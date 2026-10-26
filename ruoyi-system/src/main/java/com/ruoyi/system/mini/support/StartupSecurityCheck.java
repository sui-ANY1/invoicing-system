package com.ruoyi.system.mini.support;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.StringUtils;

import jakarta.annotation.PostConstruct;

/**
 * 启动安全自检。
 *
 * <p>联调态（app.security.strict=false）只打印告警；
 * 生产态（环境变量 APP_SECURITY_STRICT=true）发现任何一项不合规直接拒绝启动，
 * 避免带着内置密钥、模拟登录、默认加密密钥上线。</p>
 *
 * @author sui-ANY1
 */
@Component
public class StartupSecurityCheck {

    private static final Logger log = LoggerFactory.getLogger(StartupSecurityCheck.class);

    /** 与 MiniTokenService、application.yml 默认值保持一致的内置开发密钥 */
    private static final String DEV_SECRET = "abcdefghijklmnopqrstuvwxyz";

    /** 与 application.yml 中 mini.crypto.key 的默认值保持一致 */
    private static final String DEFAULT_CRYPTO_KEY = "dev-default-key-2026";

    @Value("${app.security.strict:false}")
    private boolean strict;

    @Value("${token.secret:}")
    private String tokenSecret;

    @Value("${mini.token.secret:}")
    private String miniSecret;

    @Value("${mini.wx.mock-enabled:true}")
    private boolean wxMockEnabled;

    @Value("${mini.crypto.key:}")
    private String cryptoKey;

    @PostConstruct
    public void check() {
        List<String> problems = new ArrayList<>();

        if (DEV_SECRET.equals(tokenSecret)) {
            problems.add("TOKEN_SECRET 仍是内置开发密钥 abc…，PC 令牌可被伪造");
        }
        if (StringUtils.isEmpty(miniSecret)) {
            problems.add("MINI_TOKEN_SECRET 未配置，小程序令牌回退使用后台密钥（两套 token 同钥）");
        } else if (DEV_SECRET.equals(miniSecret)) {
            problems.add("MINI_TOKEN_SECRET 仍是内置开发密钥");
        }
        if (wxMockEnabled) {
            problems.add("MINI_WX_MOCK_ENABLED=true，任意 code 都能换取登录态");
        }
        if (StringUtils.isEmpty(cryptoKey) || DEFAULT_CRYPTO_KEY.equals(cryptoKey)) {
            problems.add("MINI_CRYPTO_KEY 仍是默认值，税务账号密码的加密形同明文");
        }

        if (problems.isEmpty()) {
            log.info("[启动自检] 安全配置检查通过");
            return;
        }
        if (strict) {
            throw new IllegalStateException("生产安全检查未通过，拒绝启动：\n - "
                    + String.join("\n - ", problems));
        }
        problems.forEach(p -> log.warn("[启动自检] {}", p));
    }
}
