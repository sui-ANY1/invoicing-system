package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 开票企业对象 t_enterprise
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class Enterprise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主体 id */
    @Schema(description = "主体ID", example = "9001")
    private Long enterpriseId;

    /** 归属小程序用户 id */
    @Schema(description = "归属小程序用户ID", example = "1")
    @Excel(name = "归属小程序用户 id")
    private Long memberId;

    /** 主体类型（1公司 2个体工商户） */
    @Schema(description = "主体类型：1公司 2个体工商户", example = "1")
    @Excel(name = "主体类型", readConverterExp = "1=公司,2=个体工商户")
    private String enterpriseType;

    /** 主体名称（营业执照名称） */
    @Schema(description = "主体名称（营业执照名称）", example = "某某市某某科技有限公司")
    @Excel(name = "主体名称", readConverterExp = "营=业执照名称")
    private String enterpriseName;

    /** 统一社会信用代码／税号 */
    @Schema(description = "统一社会信用代码／税号", example = "91320500MA1XXXXX0A")
    @Excel(name = "统一社会信用代码／税号")
    private String taxNumber;

    /** 主体登记信息（地址等，按营业执照填写） */
    @Schema(description = "主体登记信息（注册地址等，按营业执照填写）", example = "某某市工业园区XX路1号")
    @Excel(name = "主体登记信息", readConverterExp = "地=址等，按营业执照填写")
    private String registerAddress;

    /** 营业执照附件 url */
    @Schema(description = "营业执照附件URL（平台内路径）", example = "/profile/upload/2026/10/16/license.png")
    @Excel(name = "营业执照附件 url")
    private String businessLicense;

    /** 认证状态（0/1核验中 2已认证 3认证不通过） */
    @Schema(description = "认证状态：0/1核验中 2已认证 3认证不通过", example = "2")
    @Excel(name = "认证状态", readConverterExp = "0=核验中,1=核验中,2=已认证,3=认证不通过")
    private String authStatus;

    /** 审核人 */
    @Schema(description = "审核人账号", example = "admin")
    @Excel(name = "审核人")
    private String auditBy;

    /** 审核时间 */
    @Schema(description = "审核时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核意见／不通过原因 */
    @Schema(description = "审核意见／不通过原因", example = "资料清晰，通过")
    @Excel(name = "审核意见／不通过原因")
    private String auditRemark;

    /** 状态（0正常 1停用） */
    @Schema(description = "主体状态：0正常 1停用", example = "0")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0存在 2删除） */
    @Schema(description = "删除标志：0存在 2删除", example = "0")
    private String delFlag;

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setEnterpriseType(String enterpriseType) 
    {
        this.enterpriseType = enterpriseType;
    }

    public String getEnterpriseType() 
    {
        return enterpriseType;
    }

    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }

    public void setTaxNumber(String taxNumber) 
    {
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() 
    {
        return taxNumber;
    }

    public void setRegisterAddress(String registerAddress) 
    {
        this.registerAddress = registerAddress;
    }

    public String getRegisterAddress() 
    {
        return registerAddress;
    }

    public void setBusinessLicense(String businessLicense) 
    {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() 
    {
        return businessLicense;
    }

    public void setAuthStatus(String authStatus) 
    {
        this.authStatus = authStatus;
    }

    public String getAuthStatus() 
    {
        return authStatus;
    }

    public void setAuditBy(String auditBy) 
    {
        this.auditBy = auditBy;
    }

    public String getAuditBy() 
    {
        return auditBy;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    public void setAuditRemark(String auditRemark) 
    {
        this.auditRemark = auditRemark;
    }

    public String getAuditRemark() 
    {
        return auditRemark;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("enterpriseId", getEnterpriseId())
            .append("memberId", getMemberId())
            .append("enterpriseType", getEnterpriseType())
            .append("enterpriseName", getEnterpriseName())
            .append("taxNumber", getTaxNumber())
            .append("registerAddress", getRegisterAddress())
            .append("businessLicense", getBusinessLicense())
            .append("authStatus", getAuthStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("auditRemark", getAuditRemark())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
