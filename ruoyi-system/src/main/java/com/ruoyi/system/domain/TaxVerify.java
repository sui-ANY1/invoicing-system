package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 税务验证对象 t_tax_verify
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class TaxVerify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 验证记录 id */
    @Schema(description = "验证记录ID", example = "1")
    private Long verifyId;

    /** 业务类型（1蓝字开票 2红字） */
    @Schema(description = "业务类型（1蓝字开票 2红字）", example = "1")
    @Excel(name = "业务类型", readConverterExp = "1=蓝字开票,2=红字")
    private String bizType;

    /** 关联业务 id（申请/红字 id） */
    @Schema(description = "关联业务ID（申请/红字ID）", example = "1")
    @Excel(name = "关联业务 id", readConverterExp = "申=请/红字,i=d")
    private Long bizId;

    /** 验证方式（1短信，本期仅支持短信） */
    @Schema(description = "验证方式（1短信，本期仅支持短信）", example = "1")
    @Excel(name = "验证方式", readConverterExp = "1=短信，本期仅支持短信")
    private String verifyType;

    /** 接收账号（手机号，脱敏存储） */
    @Schema(description = "接收账号（手机号，脱敏存储）", example = "138****0000")
    @Excel(name = "接收账号", readConverterExp = "手=机号，脱敏存储")
    private String targetAccount;

    /** 验证标识（验证会话/RPA 标识） */
    @Schema(description = "验证标识（验证会话/RPA标识）", example = "VS2026101600001")
    @Excel(name = "验证标识", readConverterExp = "验=证会话/RPA,标=识")
    private String verifyKey;

    /** 状态（0已发起待验证 1已提交待代填 2验证通过 3验证失败/过期） */
    @Schema(description = "状态（0已发起待验证 1已提交待代填 2验证通过 3验证失败/过期）", example = "0")
    @Excel(name = "状态", readConverterExp = "0=已发起待验证,1=已提交待代填,2=验证通过,3=验证失败/过期")
    private String verifyStatus;

    /** 过期时间 */
    @Schema(description = "过期时间", example = "2026-10-16 10:10:00")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    public void setVerifyId(Long verifyId) 
    {
        this.verifyId = verifyId;
    }

    public Long getVerifyId() 
    {
        return verifyId;
    }

    public void setBizType(String bizType) 
    {
        this.bizType = bizType;
    }

    public String getBizType() 
    {
        return bizType;
    }

    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }

    public void setVerifyType(String verifyType) 
    {
        this.verifyType = verifyType;
    }

    public String getVerifyType() 
    {
        return verifyType;
    }

    public void setTargetAccount(String targetAccount) 
    {
        this.targetAccount = targetAccount;
    }

    public String getTargetAccount() 
    {
        return targetAccount;
    }

    public void setVerifyKey(String verifyKey) 
    {
        this.verifyKey = verifyKey;
    }

    public String getVerifyKey() 
    {
        return verifyKey;
    }

    public void setVerifyStatus(String verifyStatus) 
    {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyStatus() 
    {
        return verifyStatus;
    }

    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("verifyId", getVerifyId())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("verifyType", getVerifyType())
            .append("targetAccount", getTargetAccount())
            .append("verifyKey", getVerifyKey())
            .append("verifyStatus", getVerifyStatus())
            .append("expireTime", getExpireTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
