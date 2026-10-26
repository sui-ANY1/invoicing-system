package com.ruoyi.system.pc.vo;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * D03 一张真实发票一行（AC37：一票一行，去重依据 t_invoice.invoice_no 唯一约束）
 */
@Schema(description = "真实发票行（一票一行）")
public class PcInvoiceTicketVO {

    @Schema(description = "发票 id（下载/详情定位用）")
    private Long invoiceId;

    @Schema(description = "发票号码（唯一标识，去重依据）")
    private String invoiceNo;

    @Schema(description = "发票代码")
    private String invoiceCode;

    @Schema(description = "票种：1蓝字 2红字")
    private String invoiceKind;

    @Schema(description = "票种名称：蓝字发票 / 红字发票")
    private String invoiceKindText;

    @Schema(description = "开票申请 id（蓝票）")
    private Long applyId;

    @Schema(description = "红字申请 id（红票）")
    private Long redApplyId;

    @Schema(description = "开票类型：1普票 2专票（来自申请）")
    private String invoiceType;

    @Schema(description = "开票类型名称：普通发票 / 增值税专用发票")
    private String invoiceTypeText;

    @Schema(description = "销售方名称（票面）")
    private String sellerName;

    @Schema(description = "销售方税号（票面）")
    private String sellerTaxNumber;

    @Schema(description = "购方名称（票面）")
    private String buyerName;

    @Schema(description = "购方税号（票面）")
    private String buyerTaxNumber;

    @Schema(description = "票面金额合计（票面回传，未返回为 null）")
    private String totalAmount;

    @Schema(description = "票面税额合计（票面回传）")
    private String totalTax;

    @Schema(description = "价税合计（票面回传）")
    private String totalPriceTax;

    @Schema(description = "价税合计展示值：票面值或“待返回”")
    private String amountDisplay;

    @Schema(description = "实际开票日期（yyyy-MM-dd）")
    private String invoiceDate;

    @Schema(description = "红冲状态：0未红冲 1部分红冲 2全额红冲")
    private String redStatus;

    @Schema(description = "文件状态：0待文件 1已获取 2获取失败")
    private String fileStatus;

    @Schema(description = "PDF 文件路径")
    private String pdfUrl;

    @Schema(description = "OFD 文件路径")
    private String ofdUrl;

    @Schema(description = "XML 文件路径")
    private String xmlUrl;

    @Schema(description = "是否有可下载文件（任一格式就绪）")
    private Boolean downloadable;

    @Schema(description = "创建时间")
    private Date createTime;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceKind() {
        return invoiceKind;
    }

    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind;
    }

    public String getInvoiceKindText() {
        return invoiceKindText;
    }

    public void setInvoiceKindText(String invoiceKindText) {
        this.invoiceKindText = invoiceKindText;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getRedApplyId() {
        return redApplyId;
    }

    public void setRedApplyId(Long redApplyId) {
        this.redApplyId = redApplyId;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerTaxNumber() {
        return sellerTaxNumber;
    }

    public void setSellerTaxNumber(String sellerTaxNumber) {
        this.sellerTaxNumber = sellerTaxNumber;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotalPriceTax() {
        return totalPriceTax;
    }

    public void setTotalPriceTax(String totalPriceTax) {
        this.totalPriceTax = totalPriceTax;
    }

    public String getAmountDisplay() {
        return amountDisplay;
    }

    public void setAmountDisplay(String amountDisplay) {
        this.amountDisplay = amountDisplay;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getRedStatus() {
        return redStatus;
    }

    public void setRedStatus(String redStatus) {
        this.redStatus = redStatus;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getOfdUrl() {
        return ofdUrl;
    }

    public void setOfdUrl(String ofdUrl) {
        this.ofdUrl = ofdUrl;
    }

    public String getXmlUrl() {
        return xmlUrl;
    }

    public void setXmlUrl(String xmlUrl) {
        this.xmlUrl = xmlUrl;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
