package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 红字申请对象 t_red_apply
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RedApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 红字申请 id */
    private Long redApplyId;

    /** 红字申请单号（唯一） */
    @Excel(name = "红字申请单号", readConverterExp = "唯=一")
    private String redApplyNo;

    /** 原蓝票发票 id */
    @Excel(name = "原蓝票发票 id")
    private Long originalInvoiceId;

    /** 申请人（小程序用户 id） */
    @Excel(name = "申请人", readConverterExp = "小=程序用户,i=d")
    private Long memberId;

    /** 红字原因（1开票有误 2退货折让 3服务中止 9其他） */
    @Excel(name = "红字原因", readConverterExp = "1=开票有误,2=退货折让,3=服务中止,9=其他")
    private String redReason;

    /** 处理范围（1全部 2部分） */
    @Excel(name = "处理范围", readConverterExp = "1=全部,2=部分")
    private String redRange;

    /** 红字状态（0已提交 1待税务/对方确认 2确认到期 3处理中 4已开红票待文件 5已交付 6明确失败 7已停止） */
    @Excel(name = "红字状态", readConverterExp = "0=已提交,1=待税务/对方确认,2=确认到期,3=处理中,4=已开红票待文件,5=已交付,6=明确失败,7=已停止")
    private String redStatus;

    /** 红字确认单状态（0未发起 1已发起待确认 2已确认 3已失效） */
    @Excel(name = "红字确认单状态", readConverterExp = "0=未发起,1=已发起待确认,2=已确认,3=已失效")
    private String confirmStatus;

    /** 确认单发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认单发起时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmStartTime;

    /** 确认单失效时间（发起后 72 小时） */
    @Excel(name = "确认单失效时间", readConverterExp = "发=起后,7=2,小=时")
    private Date confirmExpireTime;

    /** 情况说明 */
    @Excel(name = "情况说明")
    private String description;

    /** 相关依据附件 url */
    @Excel(name = "相关依据附件 url")
    private String proofUrl;

    /** 失败原因 */
    @Excel(name = "失败原因")
    private String failReason;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setRedApplyId(Long redApplyId) 
    {
        this.redApplyId = redApplyId;
    }

    public Long getRedApplyId() 
    {
        return redApplyId;
    }

    public void setRedApplyNo(String redApplyNo) 
    {
        this.redApplyNo = redApplyNo;
    }

    public String getRedApplyNo() 
    {
        return redApplyNo;
    }

    public void setOriginalInvoiceId(Long originalInvoiceId) 
    {
        this.originalInvoiceId = originalInvoiceId;
    }

    public Long getOriginalInvoiceId() 
    {
        return originalInvoiceId;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setRedReason(String redReason) 
    {
        this.redReason = redReason;
    }

    public String getRedReason() 
    {
        return redReason;
    }

    public void setRedRange(String redRange) 
    {
        this.redRange = redRange;
    }

    public String getRedRange() 
    {
        return redRange;
    }

    public void setRedStatus(String redStatus) 
    {
        this.redStatus = redStatus;
    }

    public String getRedStatus() 
    {
        return redStatus;
    }

    public void setConfirmStatus(String confirmStatus) 
    {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmStatus() 
    {
        return confirmStatus;
    }

    public void setConfirmStartTime(Date confirmStartTime) 
    {
        this.confirmStartTime = confirmStartTime;
    }

    public Date getConfirmStartTime() 
    {
        return confirmStartTime;
    }

    public void setConfirmExpireTime(Date confirmExpireTime) 
    {
        this.confirmExpireTime = confirmExpireTime;
    }

    public Date getConfirmExpireTime() 
    {
        return confirmExpireTime;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setProofUrl(String proofUrl) 
    {
        this.proofUrl = proofUrl;
    }

    public String getProofUrl() 
    {
        return proofUrl;
    }

    public void setFailReason(String failReason) 
    {
        this.failReason = failReason;
    }

    public String getFailReason() 
    {
        return failReason;
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
            .append("redApplyId", getRedApplyId())
            .append("redApplyNo", getRedApplyNo())
            .append("originalInvoiceId", getOriginalInvoiceId())
            .append("memberId", getMemberId())
            .append("redReason", getRedReason())
            .append("redRange", getRedRange())
            .append("redStatus", getRedStatus())
            .append("confirmStatus", getConfirmStatus())
            .append("confirmStartTime", getConfirmStartTime())
            .append("confirmExpireTime", getConfirmExpireTime())
            .append("description", getDescription())
            .append("proofUrl", getProofUrl())
            .append("failReason", getFailReason())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
