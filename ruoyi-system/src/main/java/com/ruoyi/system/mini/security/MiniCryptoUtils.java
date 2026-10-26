package com.ruoyi.system.mini.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import jakarta.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.StringUtils;

/**
 * 敏感字段 AES 加解密（用于税务凭据密码）
 *
 * <p>安全口径：
 * <ul>
 * <li>新密文统一 AES-256-GCM（每次随机 12 字节 IV + 128 位认证标签），格式 {@code v2:Base64(IV|密文|标签)}；
 * 同一明文每次加密结果不同，篡改可检测，不再使用 ECB。</li>
 * <li>密钥经 SHA-256 派生为 256 位，避免直接截断/补零带来的低熵密钥；密钥版本取密钥指纹（前 8 位十六进制），
 * 写入 t_tax_credential.secret_key_version，支持按版本平滑轮换。</li>
 * <li>兼容历史 AES/ECB/PKCS5Padding 密文（无前缀的 Base64）：解密时按「当前密钥 → 历史密钥」顺序尝试，
 * 因此老数据无需回刷即可继续读取。</li>
 * <li>密钥全部来自配置/环境变量：mini.crypto.key（当前）、mini.crypto.key-old（历史，逗号分隔）；
 * 仍在用内置开发密钥时启动告警。</li>
 * </ul>
 *
 * @author sui-ANY1
 */
@Component
public class MiniCryptoUtils {
    private static final Logger log = LoggerFactory.getLogger(MiniCryptoUtils.class);

    /** 新版密文前缀（AES-256-GCM） */
    public static final String PREFIX_V2 = "v2:";

    /** 内置开发密钥（仅本地联调；生产必须用环境变量 MINI_CRYPTO_KEY 覆盖） */
    private static final String DEV_KEY = "dev-default-key-2026";

    private static final String ALGORITHM = "AES";
    private static final String GCM_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String LEGACY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BIT = 128;
    private static final int LEGACY_KEY_LENGTH = 16;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Value("${mini.crypto.key:" + DEV_KEY + "}")
    private String key;

    /** 历史密钥（逗号分隔），仅用于解密旧密文 */
    @Value("${mini.crypto.key-old:}")
    private String oldKeys;

    /** 当前密钥版本（密钥指纹），延迟计算 */
    private volatile String keyVersion;

    @PostConstruct
    public void init() {
        keyVersion = fingerprint(key);
        if (StringUtils.isEmpty(key) || DEV_KEY.equals(key)) {
            log.warn("mini.crypto.key 使用内置开发密钥，税务账号密码为敏感凭据："
                    + "生产环境必须通过环境变量 MINI_CRYPTO_KEY 配置独立强密钥（≥32 位随机串），"
                    + "轮换时把旧密钥放入 MINI_CRYPTO_KEY_OLD（逗号分隔）以便旧密文继续解密");
        }
        int legacy = parseKeys(oldKeys).size();
        if (legacy > 0) {
            log.info("已加载 {} 个历史加密密钥，用于解密旧版本密文", legacy);
        }
    }

    /**
     * 当前密钥版本（写入 t_tax_credential.secret_key_version）
     */
    public String keyVersion() {
        String version = keyVersion;
        if (version == null) {
            version = fingerprint(key);
            keyVersion = version;
        }
        return version;
    }

    /**
     * 加密：明文 → 版本化密文（v2，AES-256-GCM）
     */
    public String encrypt(String plain) {
        if (StringUtils.isEmpty(plain)) {
            return null;
        }
        try {
            byte[] iv = new byte[IV_LENGTH];
            RANDOM.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(GCM_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, buildGcmKey(key), new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));

            byte[] payload = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, payload, 0, iv.length);
            System.arraycopy(cipherText, 0, payload, iv.length, cipherText.length);
            return PREFIX_V2 + Base64.getEncoder().encodeToString(payload);
        } catch (Exception e) {
            throw new IllegalStateException("敏感字段加密失败", e);
        }
    }

    /**
     * 解密：兼容 v2（AES-GCM）与历史 v1（AES/ECB）密文，按当前密钥、历史密钥顺序尝试
     */
    public String decrypt(String cipherText) {
        if (StringUtils.isEmpty(cipherText)) {
            return null;
        }
        List<String> candidates = new ArrayList<>();
        candidates.add(key);
        candidates.addAll(parseKeys(oldKeys));

        boolean v2 = cipherText.startsWith(PREFIX_V2);
        String payload = v2 ? cipherText.substring(PREFIX_V2.length()) : cipherText;

        int tried = 0;
        Exception last = null;
        for (String candidate : candidates) {
            if (StringUtils.isEmpty(candidate)) {
                continue;
            }
            tried++;
            try {
                return v2 ? decryptGcm(payload, candidate) : decryptLegacy(payload, candidate);
            } catch (Exception e) {
                last = e;
            }
        }
        throw new IllegalStateException("敏感字段解密失败（已尝试 " + tried + " 个密钥版本）", last);
    }

    private String decryptGcm(String payload, String candidateKey) throws Exception {
        byte[] raw = Base64.getDecoder().decode(payload);
        if (raw.length <= IV_LENGTH) {
            throw new IllegalArgumentException("密文长度不合法");
        }
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(raw, 0, iv, 0, IV_LENGTH);
        byte[] cipherText = new byte[raw.length - IV_LENGTH];
        System.arraycopy(raw, IV_LENGTH, cipherText, 0, cipherText.length);

        Cipher cipher = Cipher.getInstance(GCM_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, buildGcmKey(candidateKey), new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    private String decryptLegacy(String payload, String candidateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(LEGACY_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, buildLegacyKey(candidateKey));
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(payload));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * GCM 密钥：任意长度口令 → SHA-256 → 256 位
     */
    private SecretKeySpec buildGcmKey(String candidateKey) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] derived = digest.digest(candidateKey.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(derived, ALGORITHM);
    }

    /**
     * 历史 ECB 密钥：保留老算法（取前 16 字节、不足补零），仅供解密旧密文
     */
    private SecretKeySpec buildLegacyKey(String candidateKey) {
        byte[] raw = candidateKey.getBytes(StandardCharsets.UTF_8);
        byte[] fixed = new byte[LEGACY_KEY_LENGTH];
        System.arraycopy(raw, 0, fixed, 0, Math.min(raw.length, LEGACY_KEY_LENGTH));
        return new SecretKeySpec(fixed, ALGORITHM);
    }

    /**
     * 密钥指纹（SHA-256 前 8 位十六进制），作为密钥版本标识
     */
    private String fingerprint(String candidateKey) {
        if (StringUtils.isEmpty(candidateKey)) {
            return "v2-unknown";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(candidateKey.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder("v2-");
            for (int i = 0; i < 4; i++) {
                sb.append(String.format("%02x", hash[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            return "v2-unknown";
        }
    }

    private List<String> parseKeys(String keys) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(keys)) {
            return list;
        }
        for (String item : keys.split(",")) {
            String trimmed = item == null ? null : item.trim();
            if (StringUtils.isNotEmpty(trimmed)) {
                list.add(trimmed);
            }
        }
        return list;
    }
}
