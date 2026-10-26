package com.ruoyi.system.pc.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * D04 开票与红字统一记录列表行
 */
@Schema(description = "统一记录列表行（开票 / 红字）")
public class PcRecordItemVO {

    @Schema(description = "记录类型：1开票 2红字")
    private String type;

    @Schema(description = "记录类型名称：开票申请 / 红字申请")
    private String typeText;

    @Schema(description = "记录 id：开票为 applyId，红字为 redApplyId")
    private Long bizId;

    @Schema(description = "记录编号：apply_no / red_apply_no")
    private String bizNo;

    @Schema(description = "企业 id")
    private Long enterpriseId;

    @Schema(description = "企业名称（销方）")
    private String enterpriseName;

    @Schema(description = "购方名称")
    private String buyerName;

    @Schema(description = "购方税号")
    private String buyerTaxNumber;

    @Schema(description = "开票类型：1普票 2专票（仅开票记录）")
    private String invoiceType;

    @Schema(description = "开票类型名称")
    private String invoiceTypeText;

    @Schema(description = "红字原因：1开票有误 2退货折让 3服务中止 9其他（仅红字记录）")
    private String redReason;

    @Schema(description = "业务状态原值（开票 apply_status / 红字 red_status）")
    private String status;

    @Schema(description = "业务状态名称")
    private String statusText;

    @Schema(description = "确认单状态（仅红字）：0未发起 1已发起待确认 2已确认 3已失效")
    private String confirmStatus;

    @Schema(description = "票面价税合计展示：票面回传值或“待返回”")
    private String amountDisplay;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "提交时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "确认单状态名称（红字记录）")
    private String confirmStatusText;

    @Schema(description = "关联真实发票张数（一票一行，申请与发票不混为一谈）")
    private Long ticketCount;

    public String getConfirmStatusText() {
        return confirmStatusText;
    }

    public void setConfirmStatusText(String confirmStatusText) {
        this.confirmStatusText = confirmStatusText;
    }

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTaxNumber() {
        return buyerTaxNumber;
    }

    public void setBuyerTaxNumber(String buyerTaxNumber) {
        this.buyerTaxNumber = buyerTaxNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTypeText() {
        return invoiceTypeText;
    }

    public void setInvoiceTypeText(String invoiceTypeText) {
        this.invoiceTypeText = invoiceTypeText;
    }

    public String getRedReason() {
        return redReason;
    }

    public void setRedReason(String redReason) {
        this.redReason = redReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getAmountDisplay() {
        return amountDisplay;
    }

    public void setAmountDisplay(String amountDisplay) {
        this.amountDisplay = amountDisplay;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
