package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 真实发票（一票一行）小程序视图
 *
 * <p>对应 P11/P12「已返回真实发票」状态下的逐张发票展示与受控下载，
 * 其中 invoiceId 是调用 GET /api/mini/invoice/{id}/file 的唯一入参。</p>
 */
@Schema(description = "真实发票（一票一行）")
public class InvoiceTicketVO {

    @Schema(description = "发票记录 id，受控下载接口 GET /api/mini/invoice/{id}/file 的入参")
    private Long invoiceId;

    @Schema(description = "发票号码（唯一标识，去重依据）")
    private String invoiceNo;

    @Schema(description = "发票代码")
    private String invoiceCode;

    @Schema(description = "发票种类：1蓝字 2红字")
    private String invoiceKind;

    @Schema(description = "实际开票日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;

    @Schema(description = "销方名称（票面快照）")
    private String sellerName;

    @Schema(description = "销方税号（票面快照）")
    private String sellerTaxNumber;

    @Schema(description = "购方名称（票面快照）")
    private String buyerName;

    @Schema(description = "购方税号（票面快照）")
    private String buyerTaxNumber;

    @Schema(description = "票面金额合计，未返回为 null（前端显示待返回）")
    private BigDecimal totalAmount;

    @Schema(description = "票面税额合计，未返回为 null（前端显示待返回）")
    private BigDecimal totalTax;

    @Schema(description = "价税合计，未返回为 null（前端显示待返回）")
    private BigDecimal totalPriceTax;

    @Schema(description = "红冲状态：0未红冲 1部分红冲 2全额红冲")
    private String redStatus;

    @Schema(description = "文件状态：0待文件 1已获取 2获取失败")
    private String fileStatus;

    @Schema(description = "PDF 文件地址（相对路径，需经受控接口获取）")
    private String pdfUrl;

    @Schema(description = "OFD 文件地址（相对路径，需经受控接口获取）")
    private String ofdUrl;

    @Schema(description = "XML 文件地址（相对路径，需经受控接口获取）")
    private String xmlUrl;

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

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalPriceTax() {
        return totalPriceTax;
    }

    public void setTotalPriceTax(BigDecimal totalPriceTax) {
        this.totalPriceTax = totalPriceTax;
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
}
