package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 红字明细对象 t_red_item
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RedItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 红字明细 id */
    private Long redItemId;

    /** 所属红字申请 id */
    @Excel(name = "所属红字申请 id")
    private Long redApplyId;

    /** 原开票明细 id */
    @Excel(name = "原开票明细 id")
    private Long originalItemId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemName;

    /** 数量（按票面，红字一般为负） */
    @Excel(name = "数量", readConverterExp = "按=票面，红字一般为负")
    private BigDecimal quantity;

    /** 票面金额（票面回传） */
    @Excel(name = "票面金额", readConverterExp = "票=面回传")
    private BigDecimal invoiceAmount;

    /** 税率（票面回传） */
    @Excel(name = "税率", readConverterExp = "票=面回传")
    private BigDecimal taxRate;

    /** 税额（票面回传） */
    @Excel(name = "税额", readConverterExp = "票=面回传")
    private BigDecimal taxAmount;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sortNo;

    public void setRedItemId(Long redItemId) 
    {
        this.redItemId = redItemId;
    }

    public Long getRedItemId() 
    {
        return redItemId;
    }

    public void setRedApplyId(Long redApplyId) 
    {
        this.redApplyId = redApplyId;
    }

    public Long getRedApplyId() 
    {
        return redApplyId;
    }

    public void setOriginalItemId(Long originalItemId) 
    {
        this.originalItemId = originalItemId;
    }

    public Long getOriginalItemId() 
    {
        return originalItemId;
    }

    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) 
    {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getInvoiceAmount() 
    {
        return invoiceAmount;
    }

    public void setTaxRate(BigDecimal taxRate) 
    {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() 
    {
        return taxRate;
    }

    public void setTaxAmount(BigDecimal taxAmount) 
    {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxAmount() 
    {
        return taxAmount;
    }

    public void setSortNo(Long sortNo) 
    {
        this.sortNo = sortNo;
    }

    public Long getSortNo() 
    {
        return sortNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("redItemId", getRedItemId())
            .append("redApplyId", getRedApplyId())
            .append("originalItemId", getOriginalItemId())
            .append("itemName", getItemName())
            .append("quantity", getQuantity())
            .append("invoiceAmount", getInvoiceAmount())
            .append("taxRate", getTaxRate())
            .append("taxAmount", getTaxAmount())
            .append("sortNo", getSortNo())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
