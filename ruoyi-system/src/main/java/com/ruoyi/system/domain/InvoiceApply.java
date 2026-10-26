package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 开票申请对象 t_invoice_apply
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class InvoiceApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 开票申请 id */
    private Long applyId;

    /** 申请单号（业务单号，唯一） */
    @Excel(name = "申请单号", readConverterExp = "业=务单号，唯一")
    private String applyNo;

    /** 申请人（小程序用户 id） */
    @Excel(name = "申请人", readConverterExp = "小=程序用户,i=d")
    private Long memberId;

    /** 销售主体 id（已认证开票企业） */
    @Excel(name = "销售主体 id", readConverterExp = "已=认证开票企业")
    private Long enterpriseId;

    /** 发票类型（1普通发票 2增值税专用发票） */
    @Excel(name = "发票类型", readConverterExp = "1=普通发票,2=增值税专用发票")
    private String invoiceType;

    /** 购买方类型（1企业/组织 2个人） */
    @Excel(name = "购买方类型", readConverterExp = "1=企业/组织,2=个人")
    private String buyerType;

    /** 购买方名称／姓名 */
    @Excel(name = "购买方名称／姓名")
    private String buyerName;

    /** 购买方税号（条件必填） */
    @Excel(name = "购买方税号", readConverterExp = "条=件必填")
    private String buyerTaxNumber;

    /** 购买方地址、电话（条件填写） */
    @Excel(name = "购买方地址、电话", readConverterExp = "条=件填写")
    private String buyerAddressPhone;

    /** 购买方开户行及账号（条件填写） */
    @Excel(name = "购买方开户行及账号", readConverterExp = "条=件填写")
    private String buyerBank;

    /** 交易证明（合同/订单）主附件 url，多个走附件表 */
    @Excel(name = "交易证明", readConverterExp = "合=同/订单")
    private String dealProof;

    /** 发票接收邮箱 */
    @Excel(name = "发票接收邮箱")
    private String receiveEmail;

    /** 开票状态（0已提交 1待税务验证 2开票处理中 3结果核对中 4已开票待文件 5交付完成 6明确失败 7停止处理中） */
    @Excel(name = "开票状态", readConverterExp = "0=已提交,1=待税务验证,2=开票处理中,3=结果核对中,4=已开票待文件,5=交付完成,6=明确失败,7=停止处理中")
    private String applyStatus;

    /** 失败原因 */
    @Excel(name = "失败原因")
    private String failReason;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }

    public void setApplyNo(String applyNo) 
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo() 
    {
        return applyNo;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setInvoiceType(String invoiceType) 
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() 
    {
        return invoiceType;
    }

    public void setBuyerType(String buyerType) 
    {
        this.buyerType = buyerType;
    }

    public String getBuyerType() 
    {
        return buyerType;
    }

    public void setBuyerName(String buyerName) 
    {
        this.buyerName = buyerName;
    }

    public String getBuyerName() 
    {
        return buyerName;
    }

    public void setBuyerTaxNumber(String buyerTaxNumber) 
    {
        this.buyerTaxNumber = buyerTaxNumber;
    }

    public String getBuyerTaxNumber() 
    {
        return buyerTaxNumber;
    }

    public void setBuyerAddressPhone(String buyerAddressPhone) 
    {
        this.buyerAddressPhone = buyerAddressPhone;
    }

    public String getBuyerAddressPhone() 
    {
        return buyerAddressPhone;
    }

    public void setBuyerBank(String buyerBank) 
    {
        this.buyerBank = buyerBank;
    }

    public String getBuyerBank() 
    {
        return buyerBank;
    }

    public void setDealProof(String dealProof) 
    {
        this.dealProof = dealProof;
    }

    public String getDealProof() 
    {
        return dealProof;
    }

    public void setReceiveEmail(String receiveEmail) 
    {
        this.receiveEmail = receiveEmail;
    }

    public String getReceiveEmail() 
    {
        return receiveEmail;
    }

    public void setApplyStatus(String applyStatus) 
    {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatus() 
    {
        return applyStatus;
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
            .append("applyId", getApplyId())
            .append("applyNo", getApplyNo())
            .append("memberId", getMemberId())
            .append("enterpriseId", getEnterpriseId())
            .append("invoiceType", getInvoiceType())
            .append("buyerType", getBuyerType())
            .append("buyerName", getBuyerName())
            .append("buyerTaxNumber", getBuyerTaxNumber())
            .append("buyerAddressPhone", getBuyerAddressPhone())
            .append("buyerBank", getBuyerBank())
            .append("dealProof", getDealProof())
            .append("receiveEmail", getReceiveEmail())
            .append("remark", getRemark())
            .append("applyStatus", getApplyStatus())
            .append("failReason", getFailReason())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
