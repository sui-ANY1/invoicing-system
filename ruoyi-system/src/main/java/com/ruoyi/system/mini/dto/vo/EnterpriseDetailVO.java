package com.ruoyi.system.mini.dto.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序-主体认证详情
 */
@Schema(description = "主体认证详情")
public class EnterpriseDetailVO {

    @Schema(description = "企业主体ID", example = "1")
    private Long id;

    @Schema(description = "主体类型：1公司 2个体工商户", example = "1")
    private String enterpriseType;

    @Schema(description = "主体名称", example = "示例科技有限公司")
    private String enterpriseName;

    @Schema(description = "统一社会信用代码/税号", example = "91320500MA123456XY")
    private String taxNumber;

    @Schema(description = "注册地址", example = "江苏省某某市工业园区测试路88号")
    private String registerAddress;

    @Schema(description = "营业执照附件URL", example = "/profile/upload/2026/10/14/x.png")
    private String businessLicense;

    @Schema(description = "认证状态：0待提交 1核验中 2已核验 3未通过", example = "2")
    private String authStatus;

    @Schema(description = "审核意见/未通过原因", example = "核验通过")
    private String auditRemark;

    @Schema(description = "审核时间（yyyy-MM-dd）", example = "2026-10-15")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    @Schema(description = "提交时间", example = "2026-10-14 12:48:45")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "经办人列表")
    private List<ContactVO> contacts;

    @Schema(description = "税务凭据（账号脱敏、密码不返回；未配置时为 null）")
    private CredentialVO credential;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ContactVO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactVO> contacts) {
        this.contacts = contacts;
    }

    public CredentialVO getCredential() {
        return credential;
    }

    public void setCredential(CredentialVO credential) {
        this.credential = credential;
    }

    /**
     * 经办人信息
     */
    @Schema(description = "经办人信息")
    public static class ContactVO {

        @Schema(description = "经办人ID", example = "1")
        private Long id;

        @Schema(description = "办理身份：1本人（法定代表人/经营者）2受托经办人", example = "1")
        private String contactIdentity;

        @Schema(description = "姓名", example = "张三")
        private String contactName;

        @Schema(description = "证件类型：1居民身份证 2其他", example = "1")
        private String idType;

        @Schema(description = "证件号码", example = "320501199001011234")
        private String idCard;

        @Schema(description = "本人手机号", example = "13800000000")
        private String phone;

        @Schema(description = "身份证明附件URL", example = "/profile/upload/2026/10/14/idcard.png")
        private String idCardUrl;

        @Schema(description = "委托书附件URL（受托经办人有值）", example = "/profile/upload/2026/10/14/proxy.png")
        private String proxyBookUrl;

        @Schema(description = "授权事项", example = "开票、取票")
        private String authScope;

        @Schema(description = "授权有效期起（yyyy-MM-dd）", example = "2026-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authStartDate;

        @Schema(description = "授权有效期止（yyyy-MM-dd）", example = "2027-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authEndDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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
     * 税务凭据信息（脱敏）
     */
    @Schema(description = "税务凭据信息（脱敏）")
    public static class CredentialVO {

        @Schema(description = "凭据ID", example = "1")
        private Long id;

        @Schema(description = "税务平台账号（脱敏显示）", example = "91****XY")
        private String taxAccount;

        @Schema(description = "授权范围", example = "开票、取票")
        private String authScope;

        @Schema(description = "授权有效期起（yyyy-MM-dd）", example = "2026-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authStartDate;

        @Schema(description = "授权有效期止（yyyy-MM-dd）", example = "2027-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date authEndDate;

        @Schema(description = "可用状态：0可用 1已撤销 2已过期", example = "0")
        private String usableStatus;

        @Schema(description = "最后验证时间（yyyy-MM-dd）", example = "2026-10-14")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date lastVerifyTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTaxAccount() {
            return taxAccount;
        }

        public void setTaxAccount(String taxAccount) {
            this.taxAccount = taxAccount;
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

        public String getUsableStatus() {
            return usableStatus;
        }

        public void setUsableStatus(String usableStatus) {
            this.usableStatus = usableStatus;
        }

        public Date getLastVerifyTime() {
            return lastVerifyTime;
        }

        public void setLastVerifyTime(Date lastVerifyTime) {
            this.lastVerifyTime = lastVerifyTime;
        }
    }
}
