package com.ruoyi.system.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体户注册资料对象 t_reg_sole
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RegSole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 个体户资料 id */
    @Schema(description = "个体户资料ID", example = "1")
    private Long soleId;

    /** 所属收件 id */
    @Schema(description = "所属收件ID", example = "1")
    @Excel(name = "所属收件 id")
    private Long regApplyId;

    /** 办理身份（1经营者本人 2受托经办人） */
    @Schema(description = "办理身份（1经营者本人 2受托经办人）", example = "1")
    @Excel(name = "办理身份", readConverterExp = "1=经营者本人,2=受托经办人")
    private String handleIdentity;

    /** 经营者姓名 */
    @Schema(description = "经营者姓名", example = "张三")
    @Excel(name = "经营者姓名")
    private String operatorName;

    /** 证件类型（1居民身份证 2其他） */
    @Schema(description = "证件类型（1居民身份证 2其他）", example = "1")
    @Excel(name = "证件类型", readConverterExp = "1=居民身份证,2=其他")
    private String operatorIdType;

    /** 证件号码 */
    @Schema(description = "证件号码", example = "320500199001011234")
    @Excel(name = "证件号码")
    private String operatorIdCard;

    /** 经营者手机号 */
    @Schema(description = "经营者手机号", example = "13800000000")
    @Excel(name = "经营者手机号")
    private String operatorPhone;

    /** 经营者住所（与经营场所分开） */
    @Schema(description = "经营者住所（与经营场所分开）", example = "某某区XX路1号")
    @Excel(name = "经营者住所", readConverterExp = "与=经营场所分开")
    private String operatorAddress;

    /** 经营形式（1个人经营 2家庭经营） */
    @Schema(description = "经营形式（1个人经营 2家庭经营）", example = "1")
    @Excel(name = "经营形式", readConverterExp = "1=个人经营,2=家庭经营")
    private String businessForm;

    /** 家庭成员及关系（家庭经营填写，多个） */
    @Schema(description = "家庭成员及关系（家庭经营填写，多个）", example = "张三（配偶）")
    @Excel(name = "家庭成员及关系", readConverterExp = "家=庭经营填写，多个")
    private String familyMembers;

    /** 拟用名称（允许无名称，可空） */
    @Schema(description = "拟用名称（允许无名称，可空）", example = "某某区某某小吃店")
    @Excel(name = "拟用名称", readConverterExp = "允=许无名称，可空")
    private String proposedName;

    /** 经营范围 */
    @Schema(description = "经营范围", example = "餐饮服务")
    @Excel(name = "经营范围")
    private String businessScope;

    /** 经营场所类型（1实体场所 2符合条件的网络场所） */
    @Schema(description = "经营场所类型（1实体场所 2符合条件的网络场所）", example = "1")
    @Excel(name = "经营场所类型", readConverterExp = "1=实体场所,2=符合条件的网络场所")
    private String premisesType;

    /** 场所地址或网络平台信息 */
    @Schema(description = "场所地址或网络平台信息", example = "某某区XX路2号")
    @Excel(name = "场所地址或网络平台信息")
    private String premisesAddress;

    public void setSoleId(Long soleId) 
    {
        this.soleId = soleId;
    }

    public Long getSoleId() 
    {
        return soleId;
    }

    public void setRegApplyId(Long regApplyId) 
    {
        this.regApplyId = regApplyId;
    }

    public Long getRegApplyId() 
    {
        return regApplyId;
    }

    public void setHandleIdentity(String handleIdentity) 
    {
        this.handleIdentity = handleIdentity;
    }

    public String getHandleIdentity() 
    {
        return handleIdentity;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    public void setOperatorIdType(String operatorIdType) 
    {
        this.operatorIdType = operatorIdType;
    }

    public String getOperatorIdType() 
    {
        return operatorIdType;
    }

    public void setOperatorIdCard(String operatorIdCard) 
    {
        this.operatorIdCard = operatorIdCard;
    }

    public String getOperatorIdCard() 
    {
        return operatorIdCard;
    }

    public void setOperatorPhone(String operatorPhone) 
    {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorPhone() 
    {
        return operatorPhone;
    }

    public void setOperatorAddress(String operatorAddress) 
    {
        this.operatorAddress = operatorAddress;
    }

    public String getOperatorAddress() 
    {
        return operatorAddress;
    }

    public void setBusinessForm(String businessForm) 
    {
        this.businessForm = businessForm;
    }

    public String getBusinessForm() 
    {
        return businessForm;
    }

    public void setFamilyMembers(String familyMembers) 
    {
        this.familyMembers = familyMembers;
    }

    public String getFamilyMembers() 
    {
        return familyMembers;
    }

    public void setProposedName(String proposedName) 
    {
        this.proposedName = proposedName;
    }

    public String getProposedName() 
    {
        return proposedName;
    }

    public void setBusinessScope(String businessScope) 
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() 
    {
        return businessScope;
    }

    public void setPremisesType(String premisesType) 
    {
        this.premisesType = premisesType;
    }

    public String getPremisesType() 
    {
        return premisesType;
    }

    public void setPremisesAddress(String premisesAddress) 
    {
        this.premisesAddress = premisesAddress;
    }

    public String getPremisesAddress() 
    {
        return premisesAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("soleId", getSoleId())
            .append("regApplyId", getRegApplyId())
            .append("handleIdentity", getHandleIdentity())
            .append("operatorName", getOperatorName())
            .append("operatorIdType", getOperatorIdType())
            .append("operatorIdCard", getOperatorIdCard())
            .append("operatorPhone", getOperatorPhone())
            .append("operatorAddress", getOperatorAddress())
            .append("businessForm", getBusinessForm())
            .append("familyMembers", getFamilyMembers())
            .append("proposedName", getProposedName())
            .append("businessScope", getBusinessScope())
            .append("premisesType", getPremisesType())
            .append("premisesAddress", getPremisesAddress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
