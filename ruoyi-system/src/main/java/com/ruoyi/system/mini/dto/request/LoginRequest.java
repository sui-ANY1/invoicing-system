package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "小程序登录请求")
public class LoginRequest {

    @Schema(description = "微信 wx.login() 得到的临时登录凭证 code", example = "061AbCdEfGhIjKlMnOpQrStUvWx",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
