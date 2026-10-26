package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 开票申请ID请求（详情/刷新/停止/重新获取文件共用）
 */
@Schema(description = "开票申请ID请求")
public class InvoiceApplyIdRequest {

    @Schema(description = "开票申请ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long applyId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
}
