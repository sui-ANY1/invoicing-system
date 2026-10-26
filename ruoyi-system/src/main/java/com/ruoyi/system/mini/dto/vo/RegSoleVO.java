package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "个体工商户注册资料")
public class RegSoleVO {

    @Schema(description = "个体户资料ID", example = "1")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
