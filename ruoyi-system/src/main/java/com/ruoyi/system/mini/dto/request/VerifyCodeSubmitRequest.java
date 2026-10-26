package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "税务短信验证码提交请求")
public class VerifyCodeSubmitRequest {

    @Schema(description = "税务验证记录ID", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long verifyId;

    @Schema(description = "电子税局下发的短信验证码", example = "123456",
            requiredMode = Schema.RequiredMode.REQUIRED, minLength = 4, maxLength = 8)
    private String code;

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
