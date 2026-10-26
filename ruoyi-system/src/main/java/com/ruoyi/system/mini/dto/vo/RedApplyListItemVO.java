package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序红字记录列表项（发票中心「红字记录」页签用）
 */
@Schema(description = "小程序-红字记录列表项")
public class RedApplyListItemVO {

    @Schema(description = "红字申请ID", example = "1")
    private Long id;

    @Schema(description = "红字申请单号", example = "HZ202610190001")
    private String redApplyNo;

    @Schema(description = "原蓝字发票ID", example = "1")
    private Long originalInvoiceId;

    @Schema(description = "原蓝字发票号码（列表展示用，可能为空）", example = "24312000000012345678")
    private String originalInvoiceNo;

    @Schema(description = "销售方/开票企业名称（列表展示用，可能为空）", example = "示例科技有限公司")
    private String enterpriseName;

    @Schema(description = "红冲原因", example = "服务中止")
    private String redReason;

    @Schema(description = "红字状态：0待提交 1处理中 2待对方确认 3确认中 4已开具 5已失败 6已撤销 7已停止",
            example = "2")
    private String redStatus;

    @Schema(description = "确认单状态：0未生成 1待确认 2已确认 3已超时", example = "1")
    private String confirmStatus;

    @Schema(description = "提交时间", example = "2026-10-19 10:00:00")
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

    public String getOriginalInvoiceNo() {
        return originalInvoiceNo;
    }

    public void setOriginalInvoiceNo(String originalInvoiceNo) {
        this.originalInvoiceNo = originalInvoiceNo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getRedReason() {
        return redReason;
    }

    public void setRedReason(String redReason) {
        this.redReason = redReason;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
