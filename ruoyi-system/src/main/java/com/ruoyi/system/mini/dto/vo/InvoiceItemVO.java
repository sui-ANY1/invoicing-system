package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "开票明细")
public class InvoiceItemVO {

    @Schema(description = "明细ID", example = "1")
    private Long id;

    @Schema(description = "项目名称（商品或服务）", example = "技术服务费")
    private String itemName;

    @Schema(description = "业务类目/税收分类", example = "信息技术服务")
    private String category;

    @Schema(description = "规格型号", example = "标准版")
    private String spec;

    @Schema(description = "数量", example = "1")
    private BigDecimal quantity;

    @Schema(description = "单位", example = "项")
    private String unit;

    @Schema(description = "原始单价（交易约定单价）", example = "10000.00")
    private BigDecimal originalPrice;

    @Schema(description = "计价口径：1含税 2不含税", example = "1")
    private String priceCaliber;

    @Schema(description = "合同约定金额", example = "10000.00")
    private BigDecimal agreedAmount;

    @Schema(description = "票面金额（RPA回传，只读）", example = "10000.00")
    private BigDecimal invoiceAmount;

    @Schema(description = "税率（RPA回传，如 0.1300，只读）", example = "0.1300")
    private BigDecimal taxRate;

    @Schema(description = "税额（RPA回传，只读）", example = "1300.00")
    private BigDecimal taxAmount;

    @Schema(description = "排序号", example = "1")
    private Long sortNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPriceCaliber() {
        return priceCaliber;
    }

    public void setPriceCaliber(String priceCaliber) {
        this.priceCaliber = priceCaliber;
    }

    public BigDecimal getAgreedAmount() {
        return agreedAmount;
    }

    public void setAgreedAmount(BigDecimal agreedAmount) {
        this.agreedAmount = agreedAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Long getSortNo() {
        return sortNo;
    }

    public void setSortNo(Long sortNo) {
        this.sortNo = sortNo;
    }
}
