package com.ruoyi.system.pc.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.EnterpriseContact;
import com.ruoyi.system.domain.TaxCredential;
import com.ruoyi.system.pc.vo.PcAttachmentVO;

/**
 * PC 企业信息弹窗聚合对象（D02-V1）
 */
public class PcEnterpriseDetailVO {

    /**
     * 主体信息
     */
    @Schema(description = "主体信息（嵌套对象 Enterprise）")
    private Enterprise enterprise;

    /**
     * 经办人列表
     */
    @Schema(description = "经办人列表（嵌套对象列表 EnterpriseContact）")
    private List<EnterpriseContact> contacts;

    /**
     * 税务账号摘要（密码字段已置空，账号脱敏）
     */
    @Schema(description = "税务账号摘要（嵌套对象 TaxCredential，密码字段已置空，账号脱敏）")
    private TaxCredential credential;

    /**
     * 受权附件（营业执照、身份证件、委托书等，按材料类型列出，不暴露文件路径）
     */
    @Schema(description = "受权附件清单（营业执照、身份证件、委托书等；文件名可见，文件流走受控下载接口）")
    private List<PcAttachmentVO> attachments;

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public List<EnterpriseContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<EnterpriseContact> contacts) {
        this.contacts = contacts;
    }

    public TaxCredential getCredential() {
        return credential;
    }

    public void setCredential(TaxCredential credential) {
        this.credential = credential;
    }

    public List<PcAttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<PcAttachmentVO> attachments) {
        this.attachments = attachments;
    }
}
