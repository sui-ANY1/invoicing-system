package com.ruoyi.system.mini.dto.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "注册收件主信息")
public class RegApplyVO {

    @Schema(description = "注册收件ID", example = "1")
    private Long id;

    @Schema(description = "收件编号", example = "REG202610151010101234")
    private String regApplyNo;

    @Schema(description = "主体类型：1有限责任公司 2个体工商户", example = "1")
    private String subjectType;

    @Schema(description = "申请联系人姓名", example = "张三")
    private String contactName;

    @Schema(description = "申请联系人手机号", example = "13800000000")
    private String contactPhone;

    @Schema(description = "拟定名称（公司填拟定公司名，个体户填拟定字号）", example = "某某市示例科技有限公司")
    private String companyName;

    @Schema(description = "完成状态：0待完成 1已完成", example = "0")
    private String completeStatus;

    @Schema(description = "完成人（办理人员）", example = "admin")
    private String completeBy;

    @Schema(description = "完成时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completeTime;

    @Schema(description = "完成备注/失败原因", example = "材料齐全，已办结")
    private String completeRemark;

    @Schema(description = "提交时间", example = "2026-10-15 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegApplyNo() {
        return regApplyNo;
    }

    public void setRegApplyNo(String regApplyNo) {
        this.regApplyNo = regApplyNo;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public String getCompleteBy() {
        return completeBy;
    }

    public void setCompleteBy(String completeBy) {
        this.completeBy = completeBy;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getCompleteRemark() {
        return completeRemark;
    }

    public void setCompleteRemark(String completeRemark) {
        this.completeRemark = completeRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
