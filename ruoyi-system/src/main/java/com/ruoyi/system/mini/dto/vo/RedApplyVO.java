package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "红字申请主信息")
public class RedApplyVO {

    @Schema(description = "红字申请ID", example = "1")
    private Long id;

    @Schema(description = "红字申请单号", example = "RED202610151010101234")
    private String redApplyNo;

    @Schema(description = "原蓝字发票ID", example = "1")
    private Long originalInvoiceId;

    @Schema(description = "红字原因：1开票有误 2退货折让 3服务中止 9其他", example = "1")
    private String redReason;

    @Schema(description = "处理范围：1全部 2部分", example = "2")
    private String redRange;

    @Schema(description = "红字状态：0已提交 1待税务/对方确认 2确认到期 3处理中 4已开红票待文件 5已交付 6明确失败 7已停止",
            example = "1")
    private String redStatus;

    @Schema(description = "红字确认单状态：0未发起 1已发起待确认 2已确认 3已失效", example = "1")
    private String confirmStatus;

    @Schema(description = "确认单发起时间", example = "2026-10-15 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmStartTime;

    @Schema(description = "确认单失效时间（发起后72小时）", example = "2026-10-18 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmExpireTime;

    @Schema(description = "情况说明", example = "发票商品名称开具有误，申请红冲重开")
    private String description;

    @Schema(description = "相关依据附件URL", example = "/profile/upload/2026/10/14/proof.png")
    private String proofUrl;

    @Schema(description = "失败原因（状态为失败时返回）", example = "对方未在72小时内确认")
    private String failReason;

    @Schema(description = "提交时间", example = "2026-10-15 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRedApplyNo() {
        return redApplyNo;
    }

    public void setRedApplyNo(String redApplyNo) {
        this.redApplyNo = redApplyNo;
    }

    public Long getOriginalInvoiceId() {
        return originalInvoiceId;
    }

    public void setOriginalInvoiceId(Long originalInvoiceId) {
        this.originalInvoiceId = originalInvoiceId;
    }

    public String getRedReason() {
        return redReason;
    }

    public void setRedReason(String redReason) {
        this.redReason = redReason;
    }

    public String getRedRange() {
        return redRange;
    }

    public void setRedRange(String redRange) {
        this.redRange = redRange;
    }

    public String getRedStatus() {
        return redStatus;
    }

    public void setRedStatus(String redStatus) {
        this.redStatus = redStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getConfirmStartTime() {
        return confirmStartTime;
    }

    public void setConfirmStartTime(Date confirmStartTime) {
        this.confirmStartTime = confirmStartTime;
    }

    public Date getConfirmExpireTime() {
        return confirmExpireTime;
    }

    public void setConfirmExpireTime(Date confirmExpireTime) {
        this.confirmExpireTime = confirmExpireTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProofUrl() {
        return proofUrl;
    }

    public void setProofUrl(String proofUrl) {
        this.proofUrl = proofUrl;
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
