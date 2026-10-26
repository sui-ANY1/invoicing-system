package com.ruoyi.system.domain;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 股东信息对象 t_reg_shareholder
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RegShareholder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 股东 id */
    @Schema(description = "股东ID", example = "1")
    private Long shareholderId;

    /** 所属收件 id */
    @Schema(description = "所属收件ID", example = "1")
    @Excel(name = "所属收件 id")
    private Long regApplyId;

    /** 股东类型（1自然人 2机构） */
    @Schema(description = "股东类型（1自然人 2机构）", example = "1")
    @Excel(name = "股东类型", readConverterExp = "1=自然人,2=机构")
    private String shareholderType;

    /** 股东姓名／机构名称 */
    @Schema(description = "股东姓名／机构名称", example = "李四")
    @Excel(name = "股东姓名／机构名称")
    private String shareholderName;

    /** 手机号（机构为联系人电话） */
    @Schema(description = "手机号（机构为联系人电话）", example = "13800000000")
    @Excel(name = "手机号", readConverterExp = "机=构为联系人电话")
    private String contactPhone;

    /** 持股比例（百分数，如 51.00，多名股东合计 100） */
    @Schema(description = "持股比例（百分数，如 51.00，多名股东合计 100）", example = "51.00")
    @Excel(name = "持股比例", readConverterExp = "百=分数，如,5=1.00，多名股东合计,1=00")
    private BigDecimal holdRatio;

    /** 机构股东资格材料 url */
    @Schema(description = "机构股东资格材料URL（平台内路径，自然人股东为空）", example = "/profile/upload/2026/10/16/qualification.png")
    @Excel(name = "机构股东资格材料 url")
    private String qualificationUrl;

    /** 排序号 */
    @Schema(description = "排序号", example = "0")
    @Excel(name = "排序号")
    private Long sortNo;

    public void setShareholderId(Long shareholderId) 
    {
        this.shareholderId = shareholderId;
    }

    public Long getShareholderId() 
    {
        return shareholderId;
    }

    public void setRegApplyId(Long regApplyId) 
    {
        this.regApplyId = regApplyId;
    }

    public Long getRegApplyId() 
    {
        return regApplyId;
    }

    public void setShareholderType(String shareholderType) 
    {
        this.shareholderType = shareholderType;
    }

    public String getShareholderType() 
    {
        return shareholderType;
    }

    public void setShareholderName(String shareholderName) 
    {
        this.shareholderName = shareholderName;
    }

    public String getShareholderName() 
    {
        return shareholderName;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setHoldRatio(BigDecimal holdRatio) 
    {
        this.holdRatio = holdRatio;
    }

    public BigDecimal getHoldRatio() 
    {
        return holdRatio;
    }

    public void setQualificationUrl(String qualificationUrl) 
    {
        this.qualificationUrl = qualificationUrl;
    }

    public String getQualificationUrl() 
    {
        return qualificationUrl;
    }

    public void setSortNo(Long sortNo) 
    {
        this.sortNo = sortNo;
    }

    public Long getSortNo() 
    {
        return sortNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shareholderId", getShareholderId())
            .append("regApplyId", getRegApplyId())
            .append("shareholderType", getShareholderType())
            .append("shareholderName", getShareholderName())
            .append("contactPhone", getContactPhone())
            .append("holdRatio", getHoldRatio())
            .append("qualificationUrl", getQualificationUrl())
            .append("sortNo", getSortNo())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
