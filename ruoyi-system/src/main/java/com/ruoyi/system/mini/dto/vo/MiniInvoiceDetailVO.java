package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序发票详情（P19 发票详情与红字资格）
 *
 * 口径说明（对应需求 AC20/AC29）：金额一律展示票面回传值；未返回（null）原样返回 null，
 * 前端显示「待返回」，不显示 0，平台不做任何重算。
 *
 * @author ruoyi
 */
@Schema(description = "小程序发票详情（含红字资格与剩余可处理金额）")
public class MiniInvoiceDetailVO {

    @Schema(description = "发票ID", example = "1")
    private Long invoiceId;

    @Schema(description = "发票种类：1蓝字 2红字", example = "1")
    private String invoiceKind;

    @Schema(description = "发票种类文字（蓝字发票/红字发票）", example = "蓝字发票")
    private String invoiceKindText;

    @Schema(description = "发票号码（票面回传值）", example = "26212000000000189001")
    private String invoiceNo;

    @Schema(description = "发票代码（票面回传值）", example = "032002100211")
    private String invoiceCode;

    @Schema(description = "开票日期（票面回传值）")
    private Date invoiceDate;

    @Schema(description = "销售方名称", example = "示例科技有限公司")
    private String sellerName;

    @Schema(description = "购买方名称", example = "示例商贸有限公司")
    private String buyerName;

    @Schema(description = "票面金额（不含税）；未回传为 null", example = "2000.00")
    private BigDecimal totalAmount;

    @Schema(description = "票面税额；未回传为 null", example = "120.00")
    private BigDecimal totalTax;

    @Schema(description = "价税合计；未回传为 null", example = "2120.00")
    private BigDecimal totalPriceTax;

    @Schema(description = "红冲状态：0未红冲 1部分红冲 2全额红冲", example = "0")
    private String redStatus;

    @Schema(description = "红冲状态文字", example = "未红冲")
    private String redStatusText;

    @Schema(description = "文件状态：0待文件 1已获取 2获取失败", example = "1")
    private String fileStatus;

    @Schema(description = "文件状态文字", example = "已获取")
    private String fileStatusText;

    @Schema(description = "是否可下载 PDF", example = "true")
    private boolean pdfAvailable;

    @Schema(description = "是否可下载 OFD", example = "false")
    private boolean ofdAvailable;

    @Schema(description = "关联开票申请ID（蓝票时有值）", example = "1")
    private Long applyId;

    @Schema(description = "关联开票申请编号（蓝票时有值）", example = "KP202610080001")
    private String applyNo;

    @Schema(description = "关联红字申请ID（红票时有值）", example = "1")
    private Long redApplyId;

    @Schema(description = "关联红字申请编号（红票时有值）", example = "HZ202610080001")
    private String redApplyNo;

    @Schema(description = "剩余可处理金额（蓝票：价税合计 − 已红冲合计；红票固定为 0）", example = "2120.00")
    private BigDecimal remainingAmount;

    @Schema(description = "在途红字申请数（0 表示当前没有进行中的红字）", example = "0")
    private long inFlightRedCount;

    @Schema(description = "当前是否可以发起红字申请", example = "true")
    private boolean canApplyRed;

    @Schema(description = "不可发起红字的原因（canApplyRed=false 时有值）", example = "该发票存在进行中的红字申请，请等待处理完成")
    private String cannotApplyRedReason;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    public String getRedStatusText() {
        return redStatusText;
    }

    public void setRedStatusText(String redStatusText) {
        this.redStatusText = redStatusText;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileStatusText() {
        return fileStatusText;
    }

    public void setFileStatusText(String fileStatusText) {
        this.fileStatusText = fileStatusText;
    }

    public boolean isPdfAvailable() {
        return pdfAvailable;
    }

    public void setPdfAvailable(boolean pdfAvailable) {
        this.pdfAvailable = pdfAvailable;
    }

    public boolean isOfdAvailable() {
        return ofdAvailable;
    }

    public void setOfdAvailable(boolean ofdAvailable) {
        this.ofdAvailable = ofdAvailable;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Long getRedApplyId() {
        return redApplyId;
    }

    public void setRedApplyId(Long redApplyId) {
        this.redApplyId = redApplyId;
    }

    public String getRedApplyNo() {
        return redApplyNo;
    }

    public void setRedApplyNo(String redApplyNo) {
        this.redApplyNo = redApplyNo;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public long getInFlightRedCount() {
        return inFlightRedCount;
    }

    public void setInFlightRedCount(long inFlightRedCount) {
        this.inFlightRedCount = inFlightRedCount;
    }

    public boolean isCanApplyRed() {
        return canApplyRed;
    }

    public void setCanApplyRed(boolean canApplyRed) {
        this.canApplyRed = canApplyRed;
    }

    public String getCannotApplyRedReason() {
        return cannotApplyRedReason;
    }

    public void setCannotApplyRedReason(String cannotApplyRedReason) {
        this.cannotApplyRedReason = cannotApplyRedReason;
    }
}
