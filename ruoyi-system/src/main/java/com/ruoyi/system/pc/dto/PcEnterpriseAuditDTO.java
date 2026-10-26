package com.ruoyi.system.pc.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PC 企业认证审核请求体
 * result：1=审核通过 2=要求补充 3=不通过
 */
public class PcEnterpriseAuditDTO {

    /**
     * 企业ID（统一 POST + JSON body，不再走路径参数）
     */
    @Schema(description = "企业ID（主体ID）", example = "9001")
    private Long enterpriseId;

    /**
     * 审核结论：1通过 2要求补充 3不通过
     */
    @Schema(description = "审核结论：1 审核通过 / 2 要求补充 / 3 不通过", example = "1", allowableValues = { "1", "2", "3" })
    @JsonAlias("auditResult")
    private String result;

    /**
     * 审核意见 / 补充要求 / 不通过原因
     */
    @Schema(description = "审核意见（result=2 时为补充要求，result=3 时为不通过原因）", example = "营业执照照片不清晰，请重新上传")
    private String opinion;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
