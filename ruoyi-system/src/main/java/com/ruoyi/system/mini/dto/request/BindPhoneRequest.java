package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "绑定/更新手机号请求")
public class BindPhoneRequest {

    @Schema(description = "手机号（模拟模式填这个，如 13800000000；与 code 二选一）", example = "13800000000")
    private String phone;

    @Schema(description = "微信 getPhoneNumber 回调 code（真实模式填这个；与 phone 二选一）")
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
