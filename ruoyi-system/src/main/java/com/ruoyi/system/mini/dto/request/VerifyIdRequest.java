package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 税务验证记录ID请求（刷新结果）
 */
@Schema(description = "税务验证记录ID请求")
public class VerifyIdRequest {

    @Schema(description = "税务验证记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long verifyId;

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }
}
