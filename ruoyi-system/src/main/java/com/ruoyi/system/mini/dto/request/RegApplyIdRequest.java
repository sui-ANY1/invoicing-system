package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 注册收件ID请求（详情查询）
 */
@Schema(description = "注册收件ID请求")
public class RegApplyIdRequest {

    @Schema(description = "注册收件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long regApplyId;

    public Long getRegApplyId() {
        return regApplyId;
    }

    public void setRegApplyId(Long regApplyId) {
        this.regApplyId = regApplyId;
    }
}
