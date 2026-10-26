package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 主体认证详情查询请求
 */
@Schema(description = "主体认证详情查询请求")
public class EnterpriseDetailRequest {

    @Schema(description = "企业主体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long enterpriseId;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
