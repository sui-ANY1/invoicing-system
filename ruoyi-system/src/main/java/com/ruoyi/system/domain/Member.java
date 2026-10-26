package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小程序用户对象 t_member
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class Member extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户 id */
    private Long memberId;

    /** 微信小程序 openid */
    @Excel(name = "微信小程序 openid")
    private String openid;

    /** 微信开放平台 unionid */
    @Excel(name = "微信开放平台 unionid")
    private String unionid;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 手机号（getPhoneNumber 授权绑定） */
    @Excel(name = "手机号", readConverterExp = "g=etPhoneNumber,授=权绑定")
    private String phone;

    /** 性别（0未知 1男 2女） */
    @Excel(name = "性别", readConverterExp = "0=未知,1=男,2=女")
    private Long gender;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }

    public void setUnionid(String unionid) 
    {
        this.unionid = unionid;
    }

    public String getUnionid() 
    {
        return unionid;
    }

    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setGender(Long gender) 
    {
        this.gender = gender;
    }

    public Long getGender() 
    {
        return gender;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("memberId", getMemberId())
            .append("openid", getOpenid())
            .append("unionid", getUnionid())
            .append("nickName", getNickName())
            .append("avatar", getAvatar())
            .append("phone", getPhone())
            .append("gender", getGender())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("lastLoginTime", getLastLoginTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
