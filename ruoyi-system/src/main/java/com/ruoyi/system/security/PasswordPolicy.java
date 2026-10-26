package com.ruoyi.system.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

/**
 * 后台账号口令强度策略
 *
 * <p>统一收敛「新增用户 / 重置密码 / 修改密码 / 自助注册」四个入口的校验，
 * 避免只在某一处做限制导致弱口令绕过：长度 8-20 位，且必须包含大写字母、
 * 小写字母、数字、特殊字符中的至少三类，同时拦截常见弱口令、含账号名的口令
 * 以及连续/重复字符（如 1234、aaaa）。</p>
 *
 * <p>校验不通过抛 {@link ServiceException}，由全局异常处理返回给前端提示。</p>
 *
 * @author sui-ANY1
 */
public final class PasswordPolicy
{
    /** 口令最小长度 */
    public static final int MIN_LENGTH = 8;

    /** 口令最大长度（与前端 maxlength 一致） */
    public static final int MAX_LENGTH = 20;

    /** 至少满足的字符种类数：大写/小写/数字/特殊字符 四选三 */
    private static final int MIN_CHAR_CLASSES = 3;

    /** 连续或重复判定长度：达到该长度即视为弱口令 */
    private static final int SEQUENCE_LENGTH = 4;

    private static final Pattern UPPER = Pattern.compile("[A-Z]");

    private static final Pattern LOWER = Pattern.compile("[a-z]");

    private static final Pattern DIGIT = Pattern.compile("[0-9]");

    private static final Pattern SPECIAL = Pattern.compile("[^A-Za-z0-9]");

    /** 常见弱口令黑名单（小写比较） */
    private static final Set<String> WEAK_PASSWORDS = new HashSet<>(Arrays.asList(
            "123456", "1234567", "12345678", "123456789", "1234567890", "111111", "11111111",
            "000000", "00000000", "666666", "888888", "password", "passw0rd", "password1",
            "admin", "admin123", "admin@123", "administrator", "root1234", "root@123",
            "qwerty", "qwerty123", "abc123", "abc123456", "a1234567", "1qaz2wsx", "1q2w3e4r",
            "ruoyi", "ruoyi123", "ruoyi@123", "iloveyou", "welcome1", "test1234", "test@123"));

    private PasswordPolicy()
    {
    }

    /**
     * 校验口令强度（不带账号上下文）
     *
     * @param password 明文口令
     */
    public static void validate(String password)
    {
        validate(password, null);
    }

    /**
     * 校验口令强度
     *
     * @param password 明文口令
     * @param username 登录账号（可为空；不为空时口令不得包含账号名）
     */
    public static void validate(String password, String username)
    {
        if (StringUtils.isEmpty(password))
        {
            throw new ServiceException("密码不能为空");
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在 " + MIN_LENGTH + " 到 " + MAX_LENGTH + " 个字符之间");
        }
        if (password.contains(" ") || password.contains("\t") || password.contains("\n"))
        {
            throw new ServiceException("密码不能包含空格等空白字符");
        }
        int charClasses = 0;
        if (UPPER.matcher(password).find())
        {
            charClasses++;
        }
        if (LOWER.matcher(password).find())
        {
            charClasses++;
        }
        if (DIGIT.matcher(password).find())
        {
            charClasses++;
        }
        if (SPECIAL.matcher(password).find())
        {
            charClasses++;
        }
        if (charClasses < MIN_CHAR_CLASSES)
        {
            throw new ServiceException("密码必须包含大写字母、小写字母、数字、特殊字符中的至少 " + MIN_CHAR_CLASSES + " 类");
        }
        String lower = password.toLowerCase();
        if (WEAK_PASSWORDS.contains(lower))
        {
            throw new ServiceException("密码过于简单，属于常见弱口令，请更换");
        }
        if (StringUtils.isNotEmpty(username) && lower.contains(username.toLowerCase()))
        {
            throw new ServiceException("密码不能包含登录账号");
        }
        if (hasRepeatOrSequence(password))
        {
            throw new ServiceException("密码不能包含 4 位及以上连续或重复字符（如 1234、abcd、aaaa）");
        }
    }

    /**
     * 是否为弱口令（不抛异常版本，便于登录后提示等场景）
     *
     * @param password 明文口令
     * @return 不符合策略返回 true
     */
    public static boolean isWeak(String password)
    {
        try
        {
            validate(password);
            return false;
        }
        catch (ServiceException e)
        {
            return true;
        }
    }

    /**
     * 是否包含弱口令特征：4 位及以上重复字符、4 位及以上连续字符（升序、降序、等步长），
     * 或相邻重复的 2~4 位片段（如 a1@a1@、abcabc、123123）
     */
    private static boolean hasRepeatOrSequence(String password)
    {
        int repeat = 1;
        for (int i = 1; i < password.length(); i++)
        {
            if (password.charAt(i) == password.charAt(i - 1))
            {
                repeat++;
                if (repeat >= SEQUENCE_LENGTH)
                {
                    return true;
                }
            }
            else
            {
                repeat = 1;
            }
        }
        int sequence = 2;
        for (int i = 2; i < password.length(); i++)
        {
            int step1 = password.charAt(i - 1) - password.charAt(i - 2);
            int step2 = password.charAt(i) - password.charAt(i - 1);
            if (step1 == step2 && (step1 == 1 || step1 == -1))
            {
                sequence++;
                if (sequence >= SEQUENCE_LENGTH)
                {
                    return true;
                }
            }
            else
            {
                sequence = 2;
            }
        }
        // 相邻重复片段：长度 2~4 的子串紧接着再出现一次
        int maxBlock = Math.min(SEQUENCE_LENGTH, password.length() / 2);
        for (int len = 2; len <= maxBlock; len++)
        {
            for (int i = 0; i + 2 * len <= password.length(); i++)
            {
                if (password.regionMatches(i, password, i + len, len))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
