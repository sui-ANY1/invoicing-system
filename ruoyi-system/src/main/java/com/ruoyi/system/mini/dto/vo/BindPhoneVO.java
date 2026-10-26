package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "绑定手机号结果")
public class BindPhoneVO {

    @Schema(description = "已绑定的手机号", example = "13800000000")
    private String phone;

    @Schema(description = "是否已绑定手机号", example = "true")
    private Boolean bindPhone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(Boolean bindPhone) {
        this.bindPhone = bindPhone;
    }
}
