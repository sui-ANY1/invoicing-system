package com.ruoyi.system.mini.dto.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 小程序-提交主体认证资料 请求体
 */
@Schema(description = "提交主体认证资料请求体")
public class EnterpriseSubmitRequest {

    @Schema(description = "企业主体信息（P04 必填）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主体信息不能为空")
    @Valid
    private EnterpriseForm enterprise;

    @Schema(description = "经办人列表（至少 1 名）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "至少填写一名经办人")
    @Valid
    private List<ContactForm> contacts;

    @Schema(description = "税务凭据（首次认证必填：税务平台账号与密码）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "首次认证需填写税务平台账号与密码")
    @Valid
    private CredentialForm credential;

    @Schema(description = "补充材料清单（可选；营业执照/经办人证件可继续用各字段的 URL，也可在此统一提交）")
    private List<MaterialForm> materials;

    public EnterpriseForm getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseForm enterprise) {
        this.enterprise = enterprise;
    }

    public List<MaterialForm> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialForm> materials) {
        this.materials = materials;
    }

    public List<ContactForm> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactForm> contacts) {
        this.contacts = contacts;
    }

    public CredentialForm getCredential() {
        return credential;
    }

    public void setCredential(CredentialForm credential) {
        this.credential = credential;
    }

    /**
     * 企业主体信息
     */
    @Schema(description = "企业主体信息")
    public static class EnterpriseForm {

        @Schema(description = "主体类型：1公司 2个体工商户（P04 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "主体类型不能为空（1公司 2个体工商户）")
        private String enterpriseType;

        @Schema(description = "主体名称（与营业执照一致，P04 必填）", example = "示例科技有限公司",
                requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100)
        @NotBlank(message = "主体名称不能为空")
        @Size(max = 100, message = "主体名称不能超过 100 个字")
        private String enterpriseName;

        @Schema(description = "统一社会信用代码/税号（18位，P04 必填）", example = "91320500MA123456XY",
                requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 18)
        @NotBlank(message = "统一社会信用代码/税号不能为空")
        @Pattern(regexp = "^[0-9A-Za-z]{15,20}$", message = "统一社会信用代码/税号格式不正确（15~20 位字母数字）")
        private String taxNumber;

        @Schema(description = "登记地址（按营业执照填写，P04 必填）", example = "江苏省某某市工业园区测试路88号",
                requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 200)
        @NotBlank(message = "登记地址不能为空")
        private String registerAddress;

        @Schema(description = "营业执照附件URL（先调上传接口得到，P04 必填）", example = "/profile/upload/2026/10/14/x.png",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "请先上传营业执照")
        private String businessLicense;

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public String getRegisterAddress() {
            return registerAddress;
        }

        public void setRegisterAddress(String registerAddress) {
            this.registerAddress = registerAddress;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }
    }

    /**
     * 经办人信息
     */
    @Schema(description = "经办人信息")
    public static class ContactForm {

        @Schema(description = "办理身份：1本人（法定代表人/经营者）2受托经办人（P04 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "办理身份不能为空（1本人 2受托经办人）")
        private String contactIdentity;

        @Schema(description = "姓名（P04 必填）", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "经办人姓名不能为空")
        private String contactName;

        @Schema(description = "证件类型：1居民身份证 2其他（P04 必填）", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "证件类型不能为空（1居民身份证 2其他）")
        private String idType;

        @Schema(description = "证件号码（P04 必填）", example = "320501199001011234",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "证件号码不能为空")
        private String idCard;

        @Schema(description = "本人手机号（P04 必填）", example = "13800000000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "本人手机号不能为空")
        @Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确（11 位，1 开头）")
        private String phone;

        @Schema(description = "身份证明附件URL（P04 适用身份证明）", example = "/profile/upload/2026/10/14/idcard.png")
        private String idCardUrl;

        @Schema(description = "委托书附件URL（受托经办人必填，本人可不传）", example = "/profile/upload/2026/10/14/proxy.png")
        private String proxyBookUrl;

        @Schema(description = "授权事项（受托经办人必填）", example = "开票、取票")
        private String authScope;

        @Schema(description = "授权有效期起（yyyy-MM-dd）", example = "2026-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authStartDate;

        @Schema(description = "授权有效期止（yyyy-MM-dd）", example = "2027-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authEndDate;

        public String getContactIdentity() {
            return contactIdentity;
        }

        public void setContactIdentity(String contactIdentity) {
            this.contactIdentity = contactIdentity;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdCardUrl() {
            return idCardUrl;
        }

        public void setIdCardUrl(String idCardUrl) {
            this.idCardUrl = idCardUrl;
        }

        public String getProxyBookUrl() {
            return proxyBookUrl;
        }

        public void setProxyBookUrl(String proxyBookUrl) {
            this.proxyBookUrl = proxyBookUrl;
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

    /**
     * 税务凭据信息
     */
    @Schema(description = "税务凭据信息")
    public static class CredentialForm {

        @Schema(description = "税务平台账号（一般为税号，P04-03 必填）", example = "91320500MA123456XY",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "税务平台账号不能为空")
        private String taxAccount;

        @Schema(description = "税务平台密码（仅提交用，后端加密存储，不会回显；P04-03 必填）", example = "Tax@123456",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "税务平台密码不能为空")
        private String taxPassword;

        @Schema(description = "授权范围", example = "开票、取票")
        private String authScope;

        @Schema(description = "授权有效期起（yyyy-MM-dd）", example = "2026-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authStartDate;

        @Schema(description = "授权有效期止（yyyy-MM-dd）", example = "2027-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authEndDate;

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
}
