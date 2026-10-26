package com.ruoyi.system.domain;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司注册资料对象 t_reg_company
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RegCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司资料 id */
    @Schema(description = "公司资料ID", example = "1")
    private Long companyId;

    /** 所属收件 id */
    @Schema(description = "所属收件ID", example = "1")
    @Excel(name = "所属收件 id")
    private Long regApplyId;

    /** 拟用公司名称 */
    @Schema(description = "拟用公司名称（名称核准用）", example = "某某市某某科技有限公司")
    @Excel(name = "拟用公司名称")
    private String proposedName;

    /** 备选名称（多个，逗号分隔） */
    @Schema(description = "备选名称（多个，逗号分隔）", example = "某某市某某网络科技有限公司,某某市某某信息科技有限公司")
    @Excel(name = "备选名称", readConverterExp = "多=个，逗号分隔")
    private String alternateNames;

    /** 注册资本（单位：万元） */
    @Schema(description = "注册资本（单位：万元）", example = "100.00")
    @Excel(name = "注册资本", readConverterExp = "单=位：万元")
    private BigDecimal registeredCapital;

    /** 经营范围 */
    @Schema(description = "经营范围", example = "技术服务、技术开发、软件销售")
    @Excel(name = "经营范围")
    private String businessScope;

    /** 法定代表人姓名 */
    @Schema(description = "法定代表人姓名", example = "张三")
    @Excel(name = "法定代表人姓名")
    private String legalPersonName;

    /** 法人证件类型（1居民身份证 2其他） */
    @Schema(description = "法人证件类型（1居民身份证 2其他）", example = "1")
    @Excel(name = "法人证件类型", readConverterExp = "1=居民身份证,2=其他")
    private String legalPersonIdType;

    /** 法人证件号码 */
    @Schema(description = "法人证件号码", example = "320500199001011234")
    @Excel(name = "法人证件号码")
    private String legalPersonIdCard;

    /** 法人本人手机号 */
    @Schema(description = "法人本人手机号", example = "13800000000")
    @Excel(name = "法人本人手机号")
    private String legalPersonPhone;

    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    public Long getCompanyId() 
    {
        return companyId;
    }

    public void setRegApplyId(Long regApplyId) 
    {
        this.regApplyId = regApplyId;
    }

    public Long getRegApplyId() 
    {
        return regApplyId;
    }

    public void setProposedName(String proposedName) 
    {
        this.proposedName = proposedName;
    }

    public String getProposedName() 
    {
        return proposedName;
    }

    public void setAlternateNames(String alternateNames) 
    {
        this.alternateNames = alternateNames;
    }

    public String getAlternateNames() 
    {
        return alternateNames;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) 
    {
        this.registeredCapital = registeredCapital;
    }

    public BigDecimal getRegisteredCapital() 
    {
        return registeredCapital;
    }

    public void setBusinessScope(String businessScope) 
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() 
    {
        return businessScope;
    }

    public void setLegalPersonName(String legalPersonName) 
    {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonName() 
    {
        return legalPersonName;
    }

    public void setLegalPersonIdType(String legalPersonIdType) 
    {
        this.legalPersonIdType = legalPersonIdType;
    }

    public String getLegalPersonIdType() 
    {
        return legalPersonIdType;
    }

    public void setLegalPersonIdCard(String legalPersonIdCard) 
    {
        this.legalPersonIdCard = legalPersonIdCard;
    }

    public String getLegalPersonIdCard() 
    {
        return legalPersonIdCard;
    }

    public void setLegalPersonPhone(String legalPersonPhone) 
    {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getLegalPersonPhone() 
    {
        return legalPersonPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("companyId", getCompanyId())
            .append("regApplyId", getRegApplyId())
            .append("proposedName", getProposedName())
            .append("alternateNames", getAlternateNames())
            .append("registeredCapital", getRegisteredCapital())
            .append("businessScope", getBusinessScope())
            .append("legalPersonName", getLegalPersonName())
            .append("legalPersonIdType", getLegalPersonIdType())
            .append("legalPersonIdCard", getLegalPersonIdCard())
            .append("legalPersonPhone", getLegalPersonPhone())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
