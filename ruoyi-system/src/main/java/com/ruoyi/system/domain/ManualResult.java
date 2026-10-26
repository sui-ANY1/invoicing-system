package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人工办理结果对象 t_manual_result
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class ManualResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 回填记录 id */
    @Schema(description = "回填记录ID", example = "1")
    private Long resultId;

    /** 业务类型（1红字 2蓝字，可扩展） */
    @Schema(description = "业务类型（1红字 2蓝字）", example = "1")
    @Excel(name = "业务类型", readConverterExp = "1=红字,2=蓝字，可扩展")
    private String bizType;

    /** 业务 id（如红字申请 id） */
    @Schema(description = "业务ID（如红字申请ID）", example = "1")
    @Excel(name = "业务 id", readConverterExp = "如=红字申请,i=d")
    private Long bizId;

    /** 回填人 */
    @Schema(description = "回填人（账号）", example = "admin")
    @Excel(name = "回填人")
    private String fillBy;

    /** 实际结果（1已交付 2办理失败 9其他） */
    @Schema(description = "实际结果（1已交付 2办理失败 9其他）", example = "1")
    @Excel(name = "实际结果", readConverterExp = "1=已交付,2=办理失败,9=其他")
    private String actualResult;

    /** 原始凭证附件 url（如红票文件） */
    @Schema(description = "原始凭证附件URL（如红票文件）", example = "/profile/upload/2026/10/16/red.pdf")
    @Excel(name = "原始凭证附件 url", readConverterExp = "如=红票文件")
    private String voucherUrl;

    /** 回填说明 */
    @Schema(description = "回填说明", example = "已线下交付红票文件")
    @Excel(name = "回填说明")
    private String fillNote;

    /** 回填时间 */
    @Schema(description = "回填时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回填时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fillTime;

    public void setResultId(Long resultId) 
    {
        this.resultId = resultId;
    }

    public Long getResultId() 
    {
        return resultId;
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

    public void setFillBy(String fillBy) 
    {
        this.fillBy = fillBy;
    }

    public String getFillBy() 
    {
        return fillBy;
    }

    public void setActualResult(String actualResult) 
    {
        this.actualResult = actualResult;
    }

    public String getActualResult() 
    {
        return actualResult;
    }

    public void setVoucherUrl(String voucherUrl) 
    {
        this.voucherUrl = voucherUrl;
    }

    public String getVoucherUrl() 
    {
        return voucherUrl;
    }

    public void setFillNote(String fillNote) 
    {
        this.fillNote = fillNote;
    }

    public String getFillNote() 
    {
        return fillNote;
    }

    public void setFillTime(Date fillTime) 
    {
        this.fillTime = fillTime;
    }

    public Date getFillTime() 
    {
        return fillTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("resultId", getResultId())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("fillBy", getFillBy())
            .append("actualResult", getActualResult())
            .append("voucherUrl", getVoucherUrl())
            .append("fillNote", getFillNote())
            .append("fillTime", getFillTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
