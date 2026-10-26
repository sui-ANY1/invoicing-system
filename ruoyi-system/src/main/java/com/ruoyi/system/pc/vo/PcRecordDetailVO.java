package com.ruoyi.system.pc.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import com.ruoyi.system.domain.AuditRecord;
import com.ruoyi.system.domain.ManualResult;
import com.ruoyi.system.domain.RpaTask;
import com.ruoyi.system.domain.TaxVerify;

/**
 * D05 记录详情聚合（申请 / 明细 / 验证 / RPA 任务 / 真实发票 / 审核留痕 / 人工回填 / 附件）
 *
 * 说明：金额字段一律取票面或申请录入原值，平台不重算；凭据只给状态不给明文口令。
 */
@Schema(description = "记录详情聚合（D05）")
public class PcRecordDetailVO {

    @Schema(description = "记录类型：1开票 2红字")
    private String type;

    @Schema(description = "记录 id")
    private Long bizId;

    @Schema(description = "记录编号")
    private String bizNo;

    @Schema(description = "业务状态原值")
    private String status;

    @Schema(description = "业务状态名称")
    private String statusText;

    @Schema(description = "申请主体（InvoiceApply 或 RedApply）")
    private Object apply;

    @Schema(description = "红字对应的原蓝票（红字记录专用）")
    private PcInvoiceTicketVO originalInvoice;

    @Schema(description = "明细行（InvoiceItem 或 RedItem，金额保留原始录入与票面回传）")
    private List<Object> items = new ArrayList<>();

    @Schema(description = "税务验证记录")
    private List<TaxVerify> verifies = new ArrayList<>();

    @Schema(description = "内部 RPA 任务（不设独立页面，仅详情内展示）")
    private List<RpaTask> rpaTasks = new ArrayList<>();

    @Schema(description = "关联真实发票（一票一行）")
    private List<PcInvoiceTicketVO> invoices = new ArrayList<>();

    @Schema(description = "审核留痕（只增不改）")
    private List<AuditRecord> audits = new ArrayList<>();

    @Schema(description = "人工结果回填留痕")
    private List<ManualResult> manualResults = new ArrayList<>();

    @Schema(description = "附件/材料清单")
    private List<PcAttachmentVO> attachments = new ArrayList<>();

    @Schema(description = "红字确认单信息（红字记录专用）")
    private RedConfirm redConfirm;

    @Schema(description = "当前状态下允许的 PC 动作（前端据此置灰按钮）")
    private List<String> allowedActions = new ArrayList<>();

    @Schema(description = "提示语（如结果未知只允许查询、已开票只允许补文件）")
    private String ruleNote;

    @Schema(description = "红字确认单信息")
    public static class RedConfirm {
        @Schema(description = "确认单状态：0未发起 1已发起待确认 2已确认 3已失效")
        private String confirmStatus;

        @Schema(description = "确认单状态名称")
        private String confirmStatusText;

        @Schema(description = "确认单发起时间")
        private Date confirmStartTime;

        @Schema(description = "确认单到期时间（发起后 72 小时）")
        private Date confirmExpireTime;

        @Schema(description = "距离到期剩余小时数（负数表示已过期）")
        private Long remainingHours;

        @Schema(description = "是否已到期")
        private Boolean expired;

        public String getConfirmStatus() {
            return confirmStatus;
        }

        public void setConfirmStatus(String confirmStatus) {
            this.confirmStatus = confirmStatus;
        }

        public String getConfirmStatusText() {
            return confirmStatusText;
        }

        public void setConfirmStatusText(String confirmStatusText) {
            this.confirmStatusText = confirmStatusText;
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

        public Long getRemainingHours() {
            return remainingHours;
        }

        public void setRemainingHours(Long remainingHours) {
            this.remainingHours = remainingHours;
        }

        public Boolean getExpired() {
            return expired;
        }

        public void setExpired(Boolean expired) {
            this.expired = expired;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Object getApply() {
        return apply;
    }

    public void setApply(Object apply) {
        this.apply = apply;
    }

    public PcInvoiceTicketVO getOriginalInvoice() {
        return originalInvoice;
    }

    public void setOriginalInvoice(PcInvoiceTicketVO originalInvoice) {
        this.originalInvoice = originalInvoice;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public List<TaxVerify> getVerifies() {
        return verifies;
    }

    public void setVerifies(List<TaxVerify> verifies) {
        this.verifies = verifies;
    }

    public List<RpaTask> getRpaTasks() {
        return rpaTasks;
    }

    public void setRpaTasks(List<RpaTask> rpaTasks) {
        this.rpaTasks = rpaTasks;
    }

    public List<PcInvoiceTicketVO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<PcInvoiceTicketVO> invoices) {
        this.invoices = invoices;
    }

    public List<AuditRecord> getAudits() {
        return audits;
    }

    public void setAudits(List<AuditRecord> audits) {
        this.audits = audits;
    }

    public List<ManualResult> getManualResults() {
        return manualResults;
    }

    public void setManualResults(List<ManualResult> manualResults) {
        this.manualResults = manualResults;
    }

    public List<PcAttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<PcAttachmentVO> attachments) {
        this.attachments = attachments;
    }

    public RedConfirm getRedConfirm() {
        return redConfirm;
    }

    public void setRedConfirm(RedConfirm redConfirm) {
        this.redConfirm = redConfirm;
    }

    public List<String> getAllowedActions() {
        return allowedActions;
    }

    public void setAllowedActions(List<String> allowedActions) {
        this.allowedActions = allowedActions;
    }

    public String getRuleNote() {
        return ruleNote;
    }

    public void setRuleNote(String ruleNote) {
        this.ruleNote = ruleNote;
    }
}
