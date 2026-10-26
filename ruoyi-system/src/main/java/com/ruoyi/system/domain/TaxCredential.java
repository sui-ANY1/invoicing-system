package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 税务凭据对象 t_tax_credential
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class TaxCredential extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 税务凭据 id */
    @Schema(description = "税务凭据ID", example = "1")
    private Long credentialId;

    /** 所属主体 id */
    @Schema(description = "所属主体ID", example = "9001")
    @Excel(name = "所属主体 id")
    private Long enterpriseId;

    /** 税务平台账号（展示时脱敏） */
    @Schema(description = "税务平台账号（展示时脱敏）", example = "91320500MA1XXXXX0A")
    @Excel(name = "税务平台账号", readConverterExp = "展=示时脱敏")
    private String taxAccount;

    /** 税务平台密码密文（AES 加密后 Base64，不存明文） */
    @Schema(description = "税务账号密码（服务端 AES-GCM 加密存储，接口不返回明文/密文）")
    @Excel(name = "税务平台密码密文", readConverterExp = "A=ES,加=密后,B=ase64，不存明文")
    private String taxPassword;

    /** 加密密钥版本（密钥独立存放，不入库） */
    @Schema(description = "加密密钥版本（密钥独立存放，不入库）", example = "v1")
    @Excel(name = "加密密钥版本", readConverterExp = "密=钥独立存放，不入库")
    private String secretKeyVersion;

    /** 授权范围（如开票、取票） */
    @Schema(description = "授权范围（如开票、取票）", example = "开票、取票")
    @Excel(name = "授权范围", readConverterExp = "如=开票、取票")
    private String authScope;

    /** 授权有效期起 */
    @Schema(description = "授权有效期起", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权有效期起", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authStartDate;

    /** 授权有效期止 */
    @Schema(description = "授权有效期止", example = "2027-10-15")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权有效期止", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authEndDate;

    /** 凭据版本（每次更新密码递增） */
    @Schema(description = "凭据版本（每次更新密码递增）", example = "1")
    @Excel(name = "凭据版本", readConverterExp = "每=次更新密码递增")
    private Long credentialVersion;

    /** 可用状态（0可用 1已撤销 2已过期） */
    @Schema(description = "可用状态（0可用 1已撤销 2已过期）", example = "0")
    @Excel(name = "可用状态", readConverterExp = "0=可用,1=已撤销,2=已过期")
    private String usableStatus;

    /** 最后验证时间 */
    @Schema(description = "最后验证时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后验证时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastVerifyTime;

    /** 撤销人 */
    @Schema(description = "撤销人（账号）", example = "admin")
    @Excel(name = "撤销人")
    private String revokeBy;

    /** 撤销时间 */
    @Schema(description = "撤销时间", example = "2026-11-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "撤销时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date revokeTime;

    /** 删除标志（0存在 2删除） */
    @Schema(description = "删除标志（0存在 2删除）", example = "0")
    private String delFlag;

    public void setCredentialId(Long credentialId) 
    {
        this.credentialId = credentialId;
    }

    public Long getCredentialId() 
    {
        return credentialId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setTaxAccount(String taxAccount) 
    {
        this.taxAccount = taxAccount;
    }

    public String getTaxAccount() 
    {
        return taxAccount;
    }

    public void setTaxPassword(String taxPassword) 
    {
        this.taxPassword = taxPassword;
    }

    public String getTaxPassword() 
    {
        return taxPassword;
    }

    public void setSecretKeyVersion(String secretKeyVersion) 
    {
        this.secretKeyVersion = secretKeyVersion;
    }

    public String getSecretKeyVersion() 
    {
        return secretKeyVersion;
    }

    public void setAuthScope(String authScope) 
    {
        this.authScope = authScope;
    }

    public String getAuthScope() 
    {
        return authScope;
    }

    public void setAuthStartDate(Date authStartDate) 
    {
        this.authStartDate = authStartDate;
    }

    public Date getAuthStartDate() 
    {
        return authStartDate;
    }

    public void setAuthEndDate(Date authEndDate) 
    {
        this.authEndDate = authEndDate;
    }

    public Date getAuthEndDate() 
    {
        return authEndDate;
    }

    public void setCredentialVersion(Long credentialVersion) 
    {
        this.credentialVersion = credentialVersion;
    }

    public Long getCredentialVersion() 
    {
        return credentialVersion;
    }

    public void setUsableStatus(String usableStatus) 
    {
        this.usableStatus = usableStatus;
    }

    public String getUsableStatus() 
    {
        return usableStatus;
    }

    public void setLastVerifyTime(Date lastVerifyTime) 
    {
        this.lastVerifyTime = lastVerifyTime;
    }

    public Date getLastVerifyTime() 
    {
        return lastVerifyTime;
    }

    public void setRevokeBy(String revokeBy) 
    {
        this.revokeBy = revokeBy;
    }

    public String getRevokeBy() 
    {
        return revokeBy;
    }

    public void setRevokeTime(Date revokeTime) 
    {
        this.revokeTime = revokeTime;
    }

    public Date getRevokeTime() 
    {
        return revokeTime;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("credentialId", getCredentialId())
            .append("enterpriseId", getEnterpriseId())
            .append("taxAccount", getTaxAccount())
            // 税务密码密文不落日志：只标记是否有值
            .append("taxPassword", getTaxPassword() == null ? null : "***")
            .append("secretKeyVersion", getSecretKeyVersion())
            .append("authScope", getAuthScope())
            .append("authStartDate", getAuthStartDate())
            .append("authEndDate", getAuthEndDate())
            .append("credentialVersion", getCredentialVersion())
            .append("usableStatus", getUsableStatus())
            .append("lastVerifyTime", getLastVerifyTime())
            .append("revokeBy", getRevokeBy())
            .append("revokeTime", getRevokeTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
