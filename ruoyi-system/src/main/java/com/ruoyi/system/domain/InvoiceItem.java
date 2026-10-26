package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 开票明细对象 t_invoice_item
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class InvoiceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细 id */
    private Long itemId;

    /** 所属开票申请 id */
    @Excel(name = "所属开票申请 id")
    private Long applyId;

    /** 项目名称（商品或服务） */
    @Excel(name = "项目名称", readConverterExp = "商=品或服务")
    private String itemName;

    /** 业务类目／税收分类 */
    @Excel(name = "业务类目／税收分类")
    private String category;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String spec;

    /** 数量（客户原始提供） */
    @Excel(name = "数量", readConverterExp = "客=户原始提供")
    private BigDecimal quantity;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 原始单价（交易约定单价） */
    @Excel(name = "原始单价", readConverterExp = "交=易约定单价")
    private BigDecimal originalPrice;

    /** 计价口径（1含税 2不含税） */
    @Excel(name = "计价口径", readConverterExp = "1=含税,2=不含税")
    private String priceCaliber;

    /** 原合同约定金额（原始录入，非系统计算） */
    @Excel(name = "原合同约定金额", readConverterExp = "原=始录入，非系统计算")
    private BigDecimal agreedAmount;

    /** 票面金额（票面回传，可空只读） */
    @Excel(name = "票面金额", readConverterExp = "票=面回传，可空只读")
    private BigDecimal invoiceAmount;

    /** 税率（票面回传，如 0.1300） */
    @Excel(name = "税率", readConverterExp = "票=面回传，如,0=.1300")
    private BigDecimal taxRate;

    /** 税额（票面回传，可空只读） */
    @Excel(name = "税额", readConverterExp = "票=面回传，可空只读")
    private BigDecimal taxAmount;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sortNo;

    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }

    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }

    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }

    public void setSpec(String spec) 
    {
        this.spec = spec;
    }

    public String getSpec() 
    {
        return spec;
    }

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }

    public void setPriceCaliber(String priceCaliber) 
    {
        this.priceCaliber = priceCaliber;
    }

    public String getPriceCaliber() 
    {
        return priceCaliber;
    }

    public void setAgreedAmount(BigDecimal agreedAmount) 
    {
        this.agreedAmount = agreedAmount;
    }

    public BigDecimal getAgreedAmount() 
    {
        return agreedAmount;
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
            .append("itemId", getItemId())
            .append("applyId", getApplyId())
            .append("itemName", getItemName())
            .append("category", getCategory())
            .append("spec", getSpec())
            .append("quantity", getQuantity())
            .append("unit", getUnit())
            .append("originalPrice", getOriginalPrice())
            .append("priceCaliber", getPriceCaliber())
            .append("agreedAmount", getAgreedAmount())
            .append("invoiceAmount", getInvoiceAmount())
            .append("taxRate", getTaxRate())
            .append("taxAmount", getTaxAmount())
            .append("sortNo", getSortNo())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
