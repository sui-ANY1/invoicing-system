package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 附件ID请求（注册附件预览/下载）
 */
@Schema(description = "附件ID请求")
public class AttachmentIdRequest {

    @Schema(description = "附件ID（t_attachment.attachment_id）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long attachmentId;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }
}
