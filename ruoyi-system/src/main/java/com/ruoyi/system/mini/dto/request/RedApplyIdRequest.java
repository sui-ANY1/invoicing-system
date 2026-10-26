package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 红字申请ID请求（详情/刷新共用）
 */
@Schema(description = "红字申请ID请求")
public class RedApplyIdRequest {

    @Schema(description = "红字申请ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long redApplyId;

    public Long getRedApplyId() {
        return redApplyId;
    }

    public void setRedApplyId(Long redApplyId) {
        this.redApplyId = redApplyId;
    }
}
