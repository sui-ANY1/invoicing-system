package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业经办人对象 t_enterprise_contact
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class EnterpriseContact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 经办人 id */
    @Schema(description = "经办人ID", example = "1")
    private Long contactId;

    /** 所属主体 id */
    @Schema(description = "所属主体ID", example = "9001")
    @Excel(name = "所属主体 id")
    private Long enterpriseId;

    /** 归属小程序用户 id */
    @Schema(description = "归属小程序用户ID", example = "1")
    @Excel(name = "归属小程序用户 id")
    private Long memberId;

    /** 办理身份（1本人：法定代表人/经营者 2受托经办人） */
    @Schema(description = "办理身份（1本人：法定代表人/经营者 2受托经办人）", example = "1")
    @Excel(name = "办理身份", readConverterExp = "1=本人：法定代表人/经营者,2=受托经办人")
    private String contactIdentity;

    /** 姓名 */
    @Schema(description = "姓名", example = "张三")
    @Excel(name = "姓名")
    private String contactName;

    /** 证件类型（1居民身份证 2其他） */
    @Schema(description = "证件类型（1居民身份证 2其他）", example = "1")
    @Excel(name = "证件类型", readConverterExp = "1=居民身份证,2=其他")
    private String idType;

    /** 证件号码 */
    @Schema(description = "证件号码", example = "320500199001011234")
    @Excel(name = "证件号码")
    private String idCard;

    /** 本人手机号 */
    @Schema(description = "本人手机号", example = "13800000000")
    @Excel(name = "本人手机号")
    private String phone;

    /** 身份证明附件 url */
    @Schema(description = "身份证明附件URL（平台内路径）", example = "/profile/upload/2026/10/16/idcard.png")
    @Excel(name = "身份证明附件 url")
    private String idCardUrl;

    /** 委托书附件 url（受托经办人必填，本人为空） */
    @Schema(description = "委托书附件URL（受托经办人必填，本人为空）", example = "/profile/upload/2026/10/16/proxy.png")
    @Excel(name = "委托书附件 url", readConverterExp = "受=托经办人必填，本人为空")
    private String proxyBookUrl;

    /** 授权事项 */
    @Schema(description = "授权事项", example = "开票、取票")
    @Excel(name = "授权事项")
    private String authScope;

    /** 授权有效期起 */
    @Schema(description = "授权有效期起", example = "2026-10-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权有效期起", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authStartDate;

    /** 授权有效期止 */
    @Schema(description = "授权有效期止", example = "2027-10-15")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权有效期止", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authEndDate;

    /** 删除标志（0存在 2删除） */
    @Schema(description = "删除标志（0存在 2删除）", example = "0")
    private String delFlag;

    public void setContactId(Long contactId) 
    {
        this.contactId = contactId;
    }

    public Long getContactId() 
    {
        return contactId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setContactIdentity(String contactIdentity) 
    {
        this.contactIdentity = contactIdentity;
    }

    public String getContactIdentity() 
    {
        return contactIdentity;
    }

    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }

    public void setIdType(String idType) 
    {
        this.idType = idType;
    }

    public String getIdType() 
    {
        return idType;
    }

    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setIdCardUrl(String idCardUrl) 
    {
        this.idCardUrl = idCardUrl;
    }

    public String getIdCardUrl() 
    {
        return idCardUrl;
    }

    public void setProxyBookUrl(String proxyBookUrl) 
    {
        this.proxyBookUrl = proxyBookUrl;
    }

    public String getProxyBookUrl() 
    {
        return proxyBookUrl;
    }

    public void setAuthScope(String authScope) 
    {
        this.authScope = authScope;
    }

    public String getAuthScope() 
    {
        return authScope;
    }

    public void setAuthStartDate(Date authStartDate) 
    {
        this.authStartDate = authStartDate;
    }

    public Date getAuthStartDate() 
    {
        return authStartDate;
    }

    public void setAuthEndDate(Date authEndDate) 
    {
        this.authEndDate = authEndDate;
    }

    public Date getAuthEndDate() 
    {
        return authEndDate;
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
            .append("contactId", getContactId())
            .append("enterpriseId", getEnterpriseId())
            .append("memberId", getMemberId())
            .append("contactIdentity", getContactIdentity())
            .append("contactName", getContactName())
            .append("idType", getIdType())
            .append("idCard", getIdCard())
            .append("phone", getPhone())
            .append("idCardUrl", getIdCardUrl())
            .append("proxyBookUrl", getProxyBookUrl())
            .append("authScope", getAuthScope())
            .append("authStartDate", getAuthStartDate())
            .append("authEndDate", getAuthEndDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
