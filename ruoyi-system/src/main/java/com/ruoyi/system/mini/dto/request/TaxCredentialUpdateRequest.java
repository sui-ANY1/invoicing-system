package com.ruoyi.system.mini.dto.request;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 税务凭据维护请求（P07-credentials 维护税务账号与授权）
 *
 * 只允许改自己能改的三类信息：账号、密码、授权范围与期限；
 * 密码留空表示「保持原密码不变」，不传不等于清空。
 */
@Schema(description = "税务凭据维护请求（账号/密码/授权范围与期限）")
public class TaxCredentialUpdateRequest {

    @Schema(description = "企业主体ID（要维护哪个主体）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "参数缺失：enterpriseId")
    private Long enterpriseId;

    @Schema(description = "税务平台账号（不传则保持原值）", example = "13800001234")
    @Size(max = 64, message = "税务平台账号长度不能超过 64")
    private String taxAccount;

    @Schema(description = "税务平台密码（不传或传空则保持原密码；传值即覆盖）", example = "Abc123456")
    @Size(max = 128, message = "税务平台密码长度不能超过 128")
    private String taxPassword;

    @Schema(description = "授权事项（不传则保持原值）", example = "开票认证及税务办理")
    @Size(max = 255, message = "授权事项长度不能超过 255")
    private String authScope;

    @Schema(description = "授权生效日期（不传则保持原值，格式 yyyy-MM-dd）")
    private Date authStartDate;

    @Schema(description = "授权到期日期（不传则保持原值，格式 yyyy-MM-dd）")
    private Date authEndDate;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getTaxAccount() {
        return taxAccount;
    }

    public void setTaxAccount(String taxAccount) {
        this.taxAccount = taxAccount;
    }

    public String getTaxPassword() {
        return taxPassword;
    }

    public void setTaxPassword(String taxPassword) {
        this.taxPassword = taxPassword;
    }

    public String getAuthScope() {
        return authScope;
    }

    public void setAuthScope(String authScope) {
        this.authScope = authScope;
    }

    public Date getAuthStartDate() {
        return authStartDate;
    }

    public void setAuthStartDate(Date authStartDate) {
        this.authStartDate = authStartDate;
    }

    public Date getAuthEndDate() {
        return authEndDate;
    }

    public void setAuthEndDate(Date authEndDate) {
        this.authEndDate = authEndDate;
    }
}
