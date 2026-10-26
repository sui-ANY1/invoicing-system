package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "开票申请主信息")
public class InvoiceApplyVO {

    @Schema(description = "开票申请ID", example = "1")
    private Long id;

    @Schema(description = "申请单号", example = "INV202610151010101234")
    private String applyNo;

    @Schema(description = "销售主体ID", example = "1")
    private Long enterpriseId;

    @Schema(description = "发票类型：1普通发票 2增值税专用发票", example = "1")
    private String invoiceType;

    @Schema(description = "购买方类型：1企业/组织 2个人", example = "1")
    private String buyerType;

    @Schema(description = "购买方名称/姓名", example = "某某市某某科技有限公司")
    private String buyerName;

    @Schema(description = "购买方税号", example = "91320500MA123456XY")
    private String buyerTaxNumber;

    @Schema(description = "购买方地址、电话", example = "某某市工业园区XX路 0512-88888888")
    private String buyerAddressPhone;

    @Schema(description = "购买方开户行及账号", example = "中国银行某某市分行 1234567890")
    private String buyerBank;

    @Schema(description = "交易证明附件URL", example = "/profile/upload/2026/10/14/contract.png")
    private String dealProof;

    @Schema(description = "发票接收邮箱", example = "buyer@example.com")
    private String receiveEmail;

    @Schema(description = "开票状态：0已提交 1待税务验证 2开票处理中 3结果核对中 4已开票待文件 5交付完成 6明确失败 7停止处理中",
            example = "2")
    private String applyStatus;

    @Schema(description = "失败原因（状态为失败时返回）", example = "购买方税号校验不通过")
    private String failReason;

    @Schema(description = "提交时间", example = "2026-10-15 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "已回传真实发票张数（0 表示尚未回传）", example = "1")
    private Integer invoiceCount;

    @Schema(description = "首张真实发票 id（仅当 invoiceCount = 1 时可直接用于受控下载；多张时请进详情按行下载）",
            example = "12")
    private Long invoiceId;

    @Schema(description = "首张真实发票号码（列表卡片展示用，未回传时为空）", example = "2632000000000000189")
    private String invoiceNo;

    @Schema(description = "开票企业名称（列表卡片展示用）", example = "示例科技有限公司")
    private String enterpriseName;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
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

    public String getBuyerAddressPhone() {
        return buyerAddressPhone;
    }

    public void setBuyerAddressPhone(String buyerAddressPhone) {
        this.buyerAddressPhone = buyerAddressPhone;
    }

    public String getBuyerBank() {
        return buyerBank;
    }

    public void setBuyerBank(String buyerBank) {
        this.buyerBank = buyerBank;
    }

    public String getDealProof() {
        return dealProof;
    }

    public void setDealProof(String dealProof) {
        this.dealProof = dealProof;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
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
}
