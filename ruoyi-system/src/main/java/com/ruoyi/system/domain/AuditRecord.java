package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 认证审核记录对象 t_audit_record
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class AuditRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审核记录 id */
    @Schema(description = "审核记录ID", example = "1")
    private Long auditRecordId;

    /** 业务类型（1主体认证 2开票申请 3红字申请） */
    @Schema(description = "业务类型（1主体认证 2开票申请 3红字申请）", example = "1")
    @Excel(name = "业务类型", readConverterExp = "1=主体认证，可扩展")
    private String bizType;

    /** 业务 id（如主体 id） */
    @Schema(description = "业务ID（如主体ID）", example = "9001")
    @Excel(name = "业务 id", readConverterExp = "如=主体,i=d")
    private Long bizId;

    /** 业务版本号（提交时版本，防止审核旧版本） */
    @Schema(description = "业务版本号（提交时版本，防止审核旧版本）", example = "1")
    @Excel(name = "业务版本号", readConverterExp = "提=交时版本，防止审核旧版本")
    private Long bizVersion;

    /** 审核人 */
    @Schema(description = "审核人（账号）", example = "admin")
    @Excel(name = "审核人")
    private String auditBy;

    /** 审核结论（1通过 2要求补充 3不通过） */
    @Schema(description = "审核结论（1通过 2要求补充 3不通过）", example = "1")
    @Excel(name = "审核结论", readConverterExp = "1=通过,2=不通过")
    private String auditResult;

    /** 审核意见／不通过原因 */
    @Schema(description = "审核意见／不通过原因", example = "资料清晰，通过")
    @Excel(name = "审核意见／不通过原因")
    private String auditOpinion;

    /** 审核时间 */
    @Schema(description = "审核时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    public void setAuditRecordId(Long auditRecordId) 
    {
        this.auditRecordId = auditRecordId;
    }

    public Long getAuditRecordId() 
    {
        return auditRecordId;
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

    public void setBizVersion(Long bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    public void setAuditBy(String auditBy) 
    {
        this.auditBy = auditBy;
    }

    public String getAuditBy() 
    {
        return auditBy;
    }

    public void setAuditResult(String auditResult) 
    {
        this.auditResult = auditResult;
    }

    public String getAuditResult() 
    {
        return auditResult;
    }

    public void setAuditOpinion(String auditOpinion) 
    {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditOpinion() 
    {
        return auditOpinion;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("auditRecordId", getAuditRecordId())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("bizVersion", getBizVersion())
            .append("auditBy", getAuditBy())
            .append("auditResult", getAuditResult())
            .append("auditOpinion", getAuditOpinion())
            .append("auditTime", getAuditTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
