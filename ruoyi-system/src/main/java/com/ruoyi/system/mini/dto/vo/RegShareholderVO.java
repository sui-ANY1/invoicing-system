package com.ruoyi.system.mini.dto.vo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "股东信息")
public class RegShareholderVO {

    @Schema(description = "股东ID", example = "1")
    private Long id;

    @Schema(description = "股东类型：1自然人 2机构", example = "1")
    private String shareholderType;

    @Schema(description = "股东姓名/机构名称", example = "张三")
    private String shareholderName;

    @Schema(description = "手机号（机构为联系人电话）", example = "13800000000")
    private String contactPhone;

    @Schema(description = "持股比例（百分数，如 51.00）", example = "51.00")
    private BigDecimal holdRatio;

    @Schema(description = "机构股东资格材料URL", example = "/profile/upload/2026/10/14/cert.png")
    private String qualificationUrl;

    @Schema(description = "排序号", example = "1")
    private Long sortNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
