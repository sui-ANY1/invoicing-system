package com.ruoyi.system.pc.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 新增记录结果（只返回本次新增记录的主键）
 *
 * @author sui-ANY1
 */
@Schema(description = "新增记录结果")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PcCreatedVO {

    @Schema(description = "新登记红票凭证的发票ID（D05-R3 录入实际红票凭证返回）", example = "9007")
    private Long invoiceId;

    @Schema(description = "新增补充材料的附件ID（D05-S2 补充材料返回）", example = "9010")
    private Long attachmentId;

    public PcCreatedVO() {
    }

    private PcCreatedVO(Long invoiceId, Long attachmentId) {
        this.invoiceId = invoiceId;
        this.attachmentId = attachmentId;
    }

    public static PcCreatedVO ofInvoice(Long invoiceId) {
        return new PcCreatedVO(invoiceId, null);
    }

    public static PcCreatedVO ofAttachment(Long attachmentId) {
        return new PcCreatedVO(null, attachmentId);
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
