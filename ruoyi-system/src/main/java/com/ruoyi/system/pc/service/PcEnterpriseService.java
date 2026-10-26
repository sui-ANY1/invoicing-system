package com.ruoyi.system.pc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.AuditRecord;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.EnterpriseContact;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.domain.TaxCredential;
import com.ruoyi.system.pc.dto.PcEnterpriseAuditDTO;
import com.ruoyi.system.pc.dto.PcEnterpriseDetailVO;
import com.ruoyi.system.pc.support.PcAttachments;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.system.service.IAuditRecordService;
import com.ruoyi.system.service.IEnterpriseContactService;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.system.service.IMessageService;
import com.ruoyi.system.service.ITaxCredentialService;

/**
 * PC 开票企业业务服务
 */
@Service
public class PcEnterpriseService {

    /** 附件业务类型：企业认证材料 */
    private static final String BIZ_ENTERPRISE = "enterprise";

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private IEnterpriseContactService contactService;

    @Autowired
    private ITaxCredentialService credentialService;

    @Autowired
    private IAuditRecordService auditRecordService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 企业分页列表（分页由 Controller 的 startPage 控制）
     */
    public List<Enterprise> list(Enterprise query) {
        return enterpriseService.selectEnterpriseList(query);
    }

    /**
     * 企业信息弹窗聚合：主体 + 经办人 + 税务账号摘要（脱敏、不返回密码）
     */
    public PcEnterpriseDetailVO detail(Long enterpriseId) {
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId);
        if (enterprise == null) {
            throw new ServiceException("企业不存在");
        }

        PcEnterpriseDetailVO vo = new PcEnterpriseDetailVO();
        vo.setEnterprise(enterprise);

        // 经办人
        EnterpriseContact cq = new EnterpriseContact();
        cq.setEnterpriseId(enterpriseId);
        vo.setContacts(contactService.selectEnterpriseContactList(cq));

        // 税务账号摘要：只取第一条；密码密文置空、账号脱敏
        TaxCredential tq = new TaxCredential();
        tq.setEnterpriseId(enterpriseId);
        List<TaxCredential> credentials = credentialService.selectTaxCredentialList(tq);
        if (credentials != null && !credentials.isEmpty()) {
            TaxCredential credential = credentials.get(0);
            credential.setTaxPassword(null);
            credential.setTaxAccount(maskAccount(credential.getTaxAccount()));
            vo.setCredential(credential);
        }

        // 受权附件：按企业认证材料清单返回，文件路径不外泄
        Attachment aq = new Attachment();
        aq.setBizType(BIZ_ENTERPRISE);
        aq.setBizId(enterpriseId);
        vo.setAttachments(PcAttachments.toVOList(attachmentService.selectAttachmentList(aq)));

        return vo;
    }

    /**
     * 企业认证审核
     * result：1通过 2要求补充 3不通过
     */
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long enterpriseId, PcEnterpriseAuditDTO dto, String auditBy) {
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId);
        if (enterprise == null) {
            throw new ServiceException("企业不存在");
        }
        if (!"1".equals(enterprise.getAuthStatus())) {
            throw new ServiceException("当前企业不是待审核状态，无法重复审核");
        }

        String result = dto.getResult();
        String opinion = dto.getOpinion();
        if (result == null
                || (!"1".equals(result) && !"2".equals(result) && !"3".equals(result))) {
            throw new ServiceException("审核结果不合法（1通过 2要求补充 3不通过）");
        }
        if (("2".equals(result) || "3".equals(result))
                && (opinion == null || opinion.trim().isEmpty())) {
            throw new ServiceException("要求补充或不通过时，审核意见必填");
        }

        String authStatus;
        String auditResult;
        String title;
        String content;
        String name = enterprise.getEnterpriseName();
        if ("1".equals(result)) {
            authStatus = "2";
            auditResult = "1";
            title = "主体认证审核通过";
            content = "您的企业「" + name + "」主体认证已通过。";
        } else if ("2".equals(result)) {
            authStatus = "0";
            auditResult = "2";
            title = "主体认证需要补充资料";
            content = "您的企业「" + name + "」认证需要补充资料：" + opinion;
        } else {
            authStatus = "3";
            auditResult = "3";
            title = "主体认证未通过";
            content = "您的企业「" + name + "」认证未通过：" + opinion;
        }

        Date now = new Date();

        // 1. 更新企业认证状态
        Enterprise update = new Enterprise();
        update.setEnterpriseId(enterpriseId);
        update.setAuthStatus(authStatus);
        update.setAuditBy(auditBy);
        update.setAuditTime(now);
        update.setAuditRemark(opinion);
        update.setUpdateBy(auditBy);
        update.setUpdateTime(now);
        enterpriseService.updateEnterprise(update);

        // 2. 写认证审核记录
        AuditRecord record = new AuditRecord();
        record.setBizType("1");
        record.setBizId(enterpriseId);
        record.setAuditBy(auditBy);
        record.setAuditResult(auditResult);
        record.setAuditOpinion(opinion);
        record.setAuditTime(now);
        record.setCreateBy(auditBy);
        record.setCreateTime(now);
        auditRecordService.insertAuditRecord(record);

        // 3. 给企业所属用户发站内消息
        Message msg = new Message();
        msg.setMemberId(enterprise.getMemberId());
        msg.setMessageType("1");
        msg.setTitle(title);
        msg.setContent(content);
        msg.setBizType("4");
        msg.setBizId(enterpriseId);
        msg.setIsRead("0");
        msg.setDelFlag("0");
        msg.setCreateBy(auditBy);
        msg.setCreateTime(now);
        messageService.insertMessage(msg);
    }

    /**
     * 账号脱敏：保留前 2 位、后 2 位，中间用 **** 代替
     */
    private String maskAccount(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        int len = value.length();
        if (len <= 4) {
            return "****";
        }
        return value.substring(0, 2) + "****" + value.substring(len - 2);
    }
}
