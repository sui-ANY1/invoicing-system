package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 小程序-企业列表项
 */
@Schema(description = "企业列表项")
public class EnterpriseListVO {

    @Schema(description = "企业主体ID", example = "1")
    private Long id;

    @Schema(description = "主体名称（与营业执照一致）", example = "示例科技有限公司")
    private String enterpriseName;

    @Schema(description = "主体类型：1公司 2个体工商户", example = "1")
    private String enterpriseType;

    @Schema(description = "统一社会信用代码/税号", example = "91320500MA123456XY")
    private String taxNumber;

    @Schema(description = "认证状态：0待提交 1核验中 2已核验 3未通过", example = "1")
    private String authStatus;

    @Schema(description = "审核意见/未通过原因", example = "营业执照不清晰，请重新上传")
    private String auditRemark;

    @Schema(description = "提交时间", example = "2026-10-14 12:48:45")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
