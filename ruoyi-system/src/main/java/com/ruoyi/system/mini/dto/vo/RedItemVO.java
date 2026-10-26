package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "红字明细")
public class RedItemVO {

    @Schema(description = "红字明细ID", example = "1")
    private Long id;

    @Schema(description = "原开票明细ID", example = "1")
    private Long originalItemId;

    @Schema(description = "项目名称", example = "技术服务费")
    private String itemName;

    @Schema(description = "红冲数量（按票面，红字一般为负数）", example = "-1")
    private BigDecimal quantity;

    @Schema(description = "票面金额（RPA回传，只读）", example = "-10000.00")
    private BigDecimal invoiceAmount;

    @Schema(description = "税率（RPA回传，如 0.1300，只读）", example = "0.1300")
    private BigDecimal taxRate;

    @Schema(description = "税额（RPA回传，只读）", example = "-1300.00")
    private BigDecimal taxAmount;

    @Schema(description = "排序号", example = "1")
    private Long sortNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalItemId() {
        return originalItemId;
    }

    public void setOriginalItemId(Long originalItemId) {
        this.originalItemId = originalItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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
