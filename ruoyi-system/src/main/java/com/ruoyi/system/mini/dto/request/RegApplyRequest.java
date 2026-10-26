package com.ruoyi.system.mini.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "注册收件提交请求")
public class RegApplyRequest {

    @Schema(description = "收件主信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "注册信息不能为空")
    @Valid
    private RegApplyForm apply;

    @Schema(description = "有限公司资料（主体类型=1 时必填）")
    @Valid
    private CompanyForm company;

    @Schema(description = "股东列表（有限公司，至少 1 名且合计 100%）")
    @Valid
    private List<ShareholderForm> shareholders;

    @Schema(description = "个体户资料（主体类型=2 时必填）")
    @Valid
    private SoleForm sole;

    @Schema(description = "适用材料清单（P14 步骤3 证件照片等；先调 /api/mini/file/upload 上传取得 url/attachmentId）")
    private List<MaterialForm> materials;

    public List<MaterialForm> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialForm> materials) {
        this.materials = materials;
    }

    public RegApplyForm getApply() {
        return apply;
    }

    public void setApply(RegApplyForm apply) {
        this.apply = apply;
    }

    public CompanyForm getCompany() {
        return company;
    }

    public void setCompany(CompanyForm company) {
        this.company = company;
    }

    public List<ShareholderForm> getShareholders() {
        return shareholders;
    }

    public void setShareholders(List<ShareholderForm> shareholders) {
        this.shareholders = shareholders;
    }

    public SoleForm getSole() {
        return sole;
    }

    public void setSole(SoleForm sole) {
        this.sole = sole;
    }

    @Schema(description = "注册收件主信息")
    public static class RegApplyForm {

        @Schema(description = "主体类型：1有限责任公司 2个体工商户", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "主体类型不能为空（1有限责任公司 2个体工商户）")
        @Pattern(regexp = "^[12]$", message = "主体类型只能是 1 有限责任公司 或 2 个体工商户")
        private String subjectType;

        @Schema(description = "申请联系人姓名", example = "张三",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "联系人姓名不能为空")
        private String contactName;

        @Schema(description = "申请联系人手机号", example = "13800000000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "联系人手机号不能为空")
        @Pattern(regexp = "^1\\d{10}$", message = "联系人手机号格式不正确（11 位，1 开头）")
        private String contactPhone;

        public String getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(String subjectType) {
            this.subjectType = subjectType;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }
    }

    @Schema(description = "有限公司注册资料")
    public static class CompanyForm {

        @Schema(description = "拟用公司名称", example = "某某市某某科技有限公司",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String proposedName;

        @Schema(description = "备选名称（多个用逗号分隔）", example = "某某市某某信息有限公司,某某市某某网络有限公司")
        private String alternateNames;

        @Schema(description = "注册资本（单位：万元）", example = "100.00")
        private BigDecimal registeredCapital;

        @Schema(description = "经营范围", example = "技术服务、技术开发、软件开发")
        private String businessScope;

        @Schema(description = "法定代表人姓名", example = "张三")
        private String legalPersonName;

        @Schema(description = "法人证件类型：1居民身份证 2其他", example = "1")
        private String legalPersonIdType;

        @Schema(description = "法人证件号码", example = "3205xxxxxxxxxxxxxx")
        private String legalPersonIdCard;

        @Schema(description = "法人本人手机号", example = "13800000000")
        private String legalPersonPhone;

        public String getProposedName() {
            return proposedName;
        }

        public void setProposedName(String proposedName) {
            this.proposedName = proposedName;
        }

        public String getAlternateNames() {
            return alternateNames;
        }

        public void setAlternateNames(String alternateNames) {
            this.alternateNames = alternateNames;
        }

        public BigDecimal getRegisteredCapital() {
            return registeredCapital;
        }

        public void setRegisteredCapital(BigDecimal registeredCapital) {
            this.registeredCapital = registeredCapital;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getLegalPersonName() {
            return legalPersonName;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }

        public String getLegalPersonIdType() {
            return legalPersonIdType;
        }

        public void setLegalPersonIdType(String legalPersonIdType) {
            this.legalPersonIdType = legalPersonIdType;
        }

        public String getLegalPersonIdCard() {
            return legalPersonIdCard;
        }

        public void setLegalPersonIdCard(String legalPersonIdCard) {
            this.legalPersonIdCard = legalPersonIdCard;
        }

        public String getLegalPersonPhone() {
            return legalPersonPhone;
        }

        public void setLegalPersonPhone(String legalPersonPhone) {
            this.legalPersonPhone = legalPersonPhone;
        }
    }

    @Schema(description = "股东信息")
    public static class ShareholderForm {

        @Schema(description = "股东类型：1自然人 2机构", example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String shareholderType;

        @Schema(description = "股东姓名/机构名称", example = "张三",
                requiredMode = Schema.RequiredMode.REQUIRED)
        private String shareholderName;

        @Schema(description = "手机号（机构为联系人电话）", example = "13800000000")
        private String contactPhone;

        @Schema(description = "持股比例（百分数，如 51.00，多名股东合计100）", example = "51.00")
        private BigDecimal holdRatio;

        @Schema(description = "机构股东资格材料URL", example = "/profile/upload/2026/10/14/cert.png")
        private String qualificationUrl;

        @Schema(description = "排序号（不传则后端按顺序生成）", example = "1")
        private Long sortNo;

        public String getShareholderType() {
            return shareholderType;
        }

        public void setShareholderType(String shareholderType) {
            this.shareholderType = shareholderType;
        }

        public String getShareholderName() {
            return shareholderName;
        }

        public void setShareholderName(String shareholderName) {
            this.shareholderName = shareholderName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public BigDecimal getHoldRatio() {
            return holdRatio;
        }

        public void setHoldRatio(BigDecimal holdRatio) {
            this.holdRatio = holdRatio;
        }

        public String getQualificationUrl() {
            return qualificationUrl;
        }

        public void setQualificationUrl(String qualificationUrl) {
            this.qualificationUrl = qualificationUrl;
        }

        public Long getSortNo() {
            return sortNo;
        }

        public void setSortNo(Long sortNo) {
            this.sortNo = sortNo;
        }
    }

    @Schema(description = "个体工商户注册资料")
    public static class SoleForm {

        @Schema(description = "办理身份：1经营者本人 2受托经办人", example = "1")
        private String handleIdentity;

        @Schema(description = "经营者姓名", example = "李四")
        private String operatorName;

        @Schema(description = "证件类型：1居民身份证 2其他", example = "1")
        private String operatorIdType;

        @Schema(description = "证件号码", example = "3205xxxxxxxxxxxxxx")
        private String operatorIdCard;

        @Schema(description = "经营者手机号", example = "13800000000")
        private String operatorPhone;

        @Schema(description = "经营者住所", example = "某某市某某小区1栋101")
        private String operatorAddress;

        @Schema(description = "经营形式：1个人经营 2家庭经营", example = "1")
        private String businessForm;

        @Schema(description = "家庭成员及关系（家庭经营填写）", example = "配偶王五")
        private String familyMembers;

        @Schema(description = "拟用名称（允许无名称，可空）", example = "某某市某某商贸行")
        private String proposedName;

        @Schema(description = "经营范围", example = "日用百货零售")
        private String businessScope;

        @Schema(description = "经营场所类型：1实体场所 2符合条件的网络场所", example = "1")
        private String premisesType;

        @Schema(description = "场所地址或网络平台信息", example = "某某市某某路1号")
        private String premisesAddress;

        public String getHandleIdentity() {
            return handleIdentity;
        }

        public void setHandleIdentity(String handleIdentity) {
            this.handleIdentity = handleIdentity;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getOperatorIdType() {
            return operatorIdType;
        }

        public void setOperatorIdType(String operatorIdType) {
            this.operatorIdType = operatorIdType;
        }

        public String getOperatorIdCard() {
            return operatorIdCard;
        }

        public void setOperatorIdCard(String operatorIdCard) {
            this.operatorIdCard = operatorIdCard;
        }

        public String getOperatorPhone() {
            return operatorPhone;
        }

        public void setOperatorPhone(String operatorPhone) {
            this.operatorPhone = operatorPhone;
        }

        public String getOperatorAddress() {
            return operatorAddress;
        }

        public void setOperatorAddress(String operatorAddress) {
            this.operatorAddress = operatorAddress;
        }

        public String getBusinessForm() {
            return businessForm;
        }

        public void setBusinessForm(String businessForm) {
            this.businessForm = businessForm;
        }

        public String getFamilyMembers() {
            return familyMembers;
        }

        public void setFamilyMembers(String familyMembers) {
            this.familyMembers = familyMembers;
        }

        public String getProposedName() {
            return proposedName;
        }

        public void setProposedName(String proposedName) {
            this.proposedName = proposedName;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getPremisesType() {
            return premisesType;
        }

        public void setPremisesType(String premisesType) {
            this.premisesType = premisesType;
        }

        public String getPremisesAddress() {
            return premisesAddress;
        }

        public void setPremisesAddress(String premisesAddress) {
            this.premisesAddress = premisesAddress;
        }
    }
}
