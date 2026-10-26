package com.ruoyi.system.mini.security;

import java.io.Serializable;

/**
 * 小程序登录用户身份（存 Redis，区别于 PC 端 LoginUser）
 *
 * @author sui-ANY1
 */
public class MiniLoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员 id（t_member.member_id）
     */
    private Long memberId;

    /**
     * 微信 openid
     */
    private String openid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录令牌（随机 uuid，作为 Redis 标识）
     */
    private String token;

    /**
     * 登录时间（毫秒）
     */
    private Long loginTime;

    /**
     * 过期时间（毫秒）
     */
    private Long expireTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
