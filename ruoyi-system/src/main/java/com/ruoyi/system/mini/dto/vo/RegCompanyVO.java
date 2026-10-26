package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "有限公司注册资料")
public class RegCompanyVO {

    @Schema(description = "公司资料ID", example = "1")
    private Long id;

    @Schema(description = "拟用公司名称", example = "某某市某某科技有限公司")
    private String proposedName;

    @Schema(description = "备选名称（多个用逗号分隔）", example = "某某市某某信息有限公司")
    private String alternateNames;

    @Schema(description = "注册资本（单位：万元）", example = "100.00")
    private BigDecimal registeredCapital;

    @Schema(description = "经营范围", example = "技术服务、软件开发")
    private String businessScope;

    @Schema(description = "法定代表人姓名", example = "张三")
    private String legalPersonName;

    @Schema(description = "法人证件类型：1居民身份证 2其他", example = "1")
    private String legalPersonIdType;

    @Schema(description = "法人证件号码", example = "3205xxxxxxxxxxxxxx")
    private String legalPersonIdCard;

    @Schema(description = "法人本人手机号", example = "13800000000")
    private String legalPersonPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
