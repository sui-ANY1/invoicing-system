package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 注册收件对象 t_reg_apply
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RegApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收件 id */
    @Schema(description = "收件ID", example = "1")
    private Long regApplyId;

    /** 收件编号（唯一） */
    @Schema(description = "收件编号（唯一）", example = "REG202610160001")
    @Excel(name = "收件编号", readConverterExp = "唯=一")
    private String regApplyNo;

    /** 主体类型（1有限责任公司 2个体工商户） */
    @Schema(description = "主体类型（1有限责任公司 2个体工商户）", example = "1")
    @Excel(name = "主体类型", readConverterExp = "1=有限责任公司,2=个体工商户")
    private String subjectType;

    /** 申请联系人姓名 */
    @Schema(description = "申请联系人姓名", example = "张三")
    @Excel(name = "申请联系人姓名")
    private String contactName;

    /** 申请联系人手机号 */
    @Schema(description = "申请联系人手机号", example = "13800000000")
    @Excel(name = "申请联系人手机号")
    private String contactPhone;

    /** 提交人（小程序用户 id） */
    @Schema(description = "提交人（小程序用户ID）", example = "1")
    @Excel(name = "提交人", readConverterExp = "小=程序用户,i=d")
    private Long memberId;

    /** 完成状态（0待完成 1已完成） */
    @Schema(description = "完成状态（0待完成 1已完成）", example = "0")
    @Excel(name = "完成状态", readConverterExp = "0=待完成,1=已完成")
    private String completeStatus;

    /** 完成人 */
    @Schema(description = "完成人（账号）", example = "admin")
    @Excel(name = "完成人")
    private String completeBy;

    /** 完成时间 */
    @Schema(description = "完成时间", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    /** 完成备注／失败原因 */
    @Schema(description = "完成备注／失败原因", example = "已办结")
    @Excel(name = "完成备注／失败原因")
    private String completeRemark;

    /** 删除标志（0存在 2删除） */
    @Schema(description = "删除标志（0存在 2删除）", example = "0")
    private String delFlag;

    public void setRegApplyId(Long regApplyId) 
    {
        this.regApplyId = regApplyId;
    }

    public Long getRegApplyId() 
    {
        return regApplyId;
    }

    public void setRegApplyNo(String regApplyNo) 
    {
        this.regApplyNo = regApplyNo;
    }

    public String getRegApplyNo() 
    {
        return regApplyNo;
    }

    public void setSubjectType(String subjectType) 
    {
        this.subjectType = subjectType;
    }

    public String getSubjectType() 
    {
        return subjectType;
    }

    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setCompleteStatus(String completeStatus) 
    {
        this.completeStatus = completeStatus;
    }

    public String getCompleteStatus() 
    {
        return completeStatus;
    }

    public void setCompleteBy(String completeBy) 
    {
        this.completeBy = completeBy;
    }

    public String getCompleteBy() 
    {
        return completeBy;
    }

    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }

    public void setCompleteRemark(String completeRemark) 
    {
        this.completeRemark = completeRemark;
    }

    public String getCompleteRemark() 
    {
        return completeRemark;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("regApplyId", getRegApplyId())
            .append("regApplyNo", getRegApplyNo())
            .append("subjectType", getSubjectType())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("memberId", getMemberId())
            .append("completeStatus", getCompleteStatus())
            .append("completeBy", getCompleteBy())
            .append("completeTime", getCompleteTime())
            .append("completeRemark", getCompleteRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
