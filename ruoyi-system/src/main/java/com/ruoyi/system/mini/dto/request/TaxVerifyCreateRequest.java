package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 发起税务验证请求：指定需要完成税务短信验证的企业，可选关联开票申请。
 */
@Schema(description = "发起税务验证请求")
public class TaxVerifyCreateRequest {

    @Schema(description = "企业主体ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long enterpriseId;

    @Schema(description = "关联的开票申请ID（可选）", example = "1")
    private Long applyId;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
}
