package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "登录结果")
public class LoginVO {

    @Schema(description = "访问令牌，后续请求放入请求头 Authorization: Bearer {token}")
    private String token;

    @Schema(description = "会员ID", example = "1")
    private Long memberId;

    @Schema(description = "手机号（未绑定时为 null）", example = "13800000000")
    private String phone;

    @Schema(description = "是否已绑定手机号", example = "true")
    private Boolean bindPhone;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

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
