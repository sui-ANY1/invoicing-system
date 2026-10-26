package com.ruoyi.system.mini.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.EnterpriseContact;
import com.ruoyi.system.domain.TaxCredential;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.request.EnterpriseSubmitRequest;
import com.ruoyi.system.mini.dto.request.TaxCredentialUpdateRequest;
import com.ruoyi.system.mini.dto.vo.EnterpriseDetailVO;
import com.ruoyi.system.mini.dto.vo.EnterpriseListVO;
import com.ruoyi.system.mini.security.MiniCryptoUtils;
import com.ruoyi.system.service.IEnterpriseContactService;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.system.service.ITaxCredentialService;

/**
 * 小程序主体认证业务编排
 */
@Service
public class MiniEnterpriseService {

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private IEnterpriseContactService contactService;

    @Autowired
    private ITaxCredentialService credentialService;

    @Autowired
    private MiniCryptoUtils cryptoUtils;

    @Autowired
    private MiniAttachService attachService;

    /**
     * 一次提交企业 + 经办人 + 税务凭据，返回企业主体 id
     */
    @Transactional(rollbackFor = Exception.class)
    public Long submit(Long memberId, EnterpriseSubmitRequest req) {
        if (req == null || req.getEnterprise() == null) {
            throw new ServiceException("提交内容不能为空");
        }
        if (req.getContacts() == null || req.getContacts().isEmpty()) {
            throw new ServiceException("至少填写一名经办人");
        }
        // 受托经办人：委托书与授权事项为条件必填（原型 P04 受托分支）
        for (EnterpriseSubmitRequest.ContactForm cf : req.getContacts()) {
            if ("2".equals(cf.getContactIdentity())) {
                if (StringUtils.isEmpty(cf.getProxyBookUrl())) {
                    throw new ServiceException("受托经办人请先上传委托书");
                }
                if (StringUtils.isEmpty(cf.getAuthScope())) {
                    throw new ServiceException("受托经办人请填写授权事项");
                }
            }
        }

        // 1. 企业主体（请求体 -> 数据库实体）
        EnterpriseSubmitRequest.EnterpriseForm ef = req.getEnterprise();
        Enterprise enterprise = new Enterprise();
        enterprise.setMemberId(memberId);
        enterprise.setEnterpriseType(ef.getEnterpriseType());
        enterprise.setEnterpriseName(ef.getEnterpriseName());
        enterprise.setTaxNumber(ef.getTaxNumber());
        enterprise.setRegisterAddress(ef.getRegisterAddress());
        enterprise.setBusinessLicense(ef.getBusinessLicense());
        enterprise.setAuthStatus("1");
        enterprise.setStatus("0");
        enterprise.setDelFlag("0");
        enterpriseService.insertEnterprise(enterprise);
        Long enterpriseId = enterprise.getEnterpriseId();

        // 2. 经办人列表
        for (EnterpriseSubmitRequest.ContactForm cf : req.getContacts()) {
            EnterpriseContact contact = new EnterpriseContact();
            contact.setEnterpriseId(enterpriseId);
            contact.setMemberId(memberId);
            contact.setContactIdentity(cf.getContactIdentity());
            contact.setContactName(cf.getContactName());
            contact.setIdType(cf.getIdType());
            contact.setIdCard(cf.getIdCard());
            contact.setPhone(cf.getPhone());
            contact.setIdCardUrl(cf.getIdCardUrl());
            contact.setProxyBookUrl(cf.getProxyBookUrl());
            contact.setAuthScope(cf.getAuthScope());
            contact.setAuthStartDate(cf.getAuthStartDate());
            contact.setAuthEndDate(cf.getAuthEndDate());
            contact.setDelFlag("0");
            contactService.insertEnterpriseContact(contact);
        }

        // 3. 税务凭据（密码加密后存储）
        if (req.getCredential() != null) {
            EnterpriseSubmitRequest.CredentialForm cf = req.getCredential();
            TaxCredential credential = new TaxCredential();
            credential.setEnterpriseId(enterpriseId);
            credential.setTaxAccount(cf.getTaxAccount());
            if (StringUtils.isNotEmpty(cf.getTaxPassword())) {
                credential.setTaxPassword(cryptoUtils.encrypt(cf.getTaxPassword()));
            }
            credential.setAuthScope(cf.getAuthScope());
            credential.setAuthStartDate(cf.getAuthStartDate());
            credential.setAuthEndDate(cf.getAuthEndDate());
            credential.setSecretKeyVersion(cryptoUtils.keyVersion());
            credential.setCredentialVersion(1L);
            credential.setUsableStatus("0");
            credential.setDelFlag("0");
            credentialService.insertTaxCredential(credential);
        }

        // 4. 材料（附件）登记：营业执照、经办人证件、受托委托材料，以及请求体显式材料清单
        attachService.bindUrl(memberId, MiniAttachConstants.BIZ_ENTERPRISE, enterpriseId,
                MiniAttachConstants.BUSINESS_LICENSE, null, ef.getBusinessLicense());
        for (EnterpriseSubmitRequest.ContactForm cf : req.getContacts()) {
            String idCardType = "1".equals(cf.getContactIdentity())
                    ? MiniAttachConstants.LEGAL_PERSON_ID_CARD_FRONT
                    : MiniAttachConstants.OPERATOR_ID_CARD_FRONT;
            attachService.bindUrl(memberId, MiniAttachConstants.BIZ_ENTERPRISE, enterpriseId, idCardType,
                    cf.getContactName(), cf.getIdCardUrl());
            attachService.bindUrl(memberId, MiniAttachConstants.BIZ_ENTERPRISE, enterpriseId,
                    MiniAttachConstants.PROXY_BOOK, cf.getContactName(), cf.getProxyBookUrl());
        }
        attachService.bind(memberId, MiniAttachConstants.BIZ_ENTERPRISE, enterpriseId, ef.getEnterpriseType(),
                req.getMaterials(), false);

        return enterpriseId;
    }

    /**
     * 当前会员的企业列表（实体 -> 列表 VO）
     */
    public List<EnterpriseListVO> listByMember(Long memberId) {
        Enterprise query = new Enterprise();
        query.setMemberId(memberId);
        List<Enterprise> list = enterpriseService.selectEnterpriseList(query);

        List<EnterpriseListVO> result = new ArrayList<>();
        if (list != null) {
            for (Enterprise e : list) {
                EnterpriseListVO vo = new EnterpriseListVO();
                vo.setId(e.getEnterpriseId());
                vo.setEnterpriseName(e.getEnterpriseName());
                vo.setEnterpriseType(e.getEnterpriseType());
                vo.setTaxNumber(e.getTaxNumber());
                vo.setAuthStatus(e.getAuthStatus());
                vo.setAuditRemark(e.getAuditRemark());
                vo.setCreateTime(e.getCreateTime());
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 主体详情（实体 -> 详情 VO），校验归属并对凭据脱敏
     */
    public EnterpriseDetailVO detail(Long memberId, Long enterpriseId) {
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId);
        if (enterprise == null) {
            throw new RuntimeException("主体不存在");
        }
        if (!memberId.equals(enterprise.getMemberId())) {
            throw new RuntimeException("无权查看该主体");
        }

        // 企业主体
        EnterpriseDetailVO vo = new EnterpriseDetailVO();
        vo.setId(enterprise.getEnterpriseId());
        vo.setEnterpriseType(enterprise.getEnterpriseType());
        vo.setEnterpriseName(enterprise.getEnterpriseName());
        vo.setTaxNumber(enterprise.getTaxNumber());
        vo.setRegisterAddress(enterprise.getRegisterAddress());
        vo.setBusinessLicense(enterprise.getBusinessLicense());
        vo.setAuthStatus(enterprise.getAuthStatus());
        vo.setAuditRemark(enterprise.getAuditRemark());
        vo.setAuditTime(enterprise.getAuditTime());
        vo.setCreateTime(enterprise.getCreateTime());

        // 经办人
        EnterpriseContact contactQuery = new EnterpriseContact();
        contactQuery.setEnterpriseId(enterpriseId);
        List<EnterpriseContact> contacts = contactService.selectEnterpriseContactList(contactQuery);
        List<EnterpriseDetailVO.ContactVO> contactVOList = new ArrayList<>();
        if (contacts != null) {
            for (EnterpriseContact c : contacts) {
                EnterpriseDetailVO.ContactVO cv = new EnterpriseDetailVO.ContactVO();
                cv.setId(c.getContactId());
                cv.setContactIdentity(c.getContactIdentity());
                cv.setContactName(c.getContactName());
                cv.setIdType(c.getIdType());
                cv.setIdCard(c.getIdCard());
                cv.setPhone(c.getPhone());
                cv.setIdCardUrl(c.getIdCardUrl());
                cv.setProxyBookUrl(c.getProxyBookUrl());
                cv.setAuthScope(c.getAuthScope());
                cv.setAuthStartDate(c.getAuthStartDate());
                cv.setAuthEndDate(c.getAuthEndDate());
                contactVOList.add(cv);
            }
        }
        vo.setContacts(contactVOList);

        // 税务凭据（账号脱敏，密码字段不返回）
        TaxCredential credentialQuery = new TaxCredential();
        credentialQuery.setEnterpriseId(enterpriseId);
        List<TaxCredential> credentialList = credentialService.selectTaxCredentialList(credentialQuery);
        TaxCredential credential = (credentialList != null && !credentialList.isEmpty())
                ? credentialList.get(0) : null;
        if (credential != null) {
            EnterpriseDetailVO.CredentialVO cv = new EnterpriseDetailVO.CredentialVO();
            cv.setId(credential.getCredentialId());
            cv.setTaxAccount(maskAccount(credential.getTaxAccount()));
            cv.setAuthScope(credential.getAuthScope());
            cv.setAuthStartDate(credential.getAuthStartDate());
            cv.setAuthEndDate(credential.getAuthEndDate());
            cv.setUsableStatus(credential.getUsableStatus());
            cv.setLastVerifyTime(credential.getLastVerifyTime());
            vo.setCredential(cv);
        }
        return vo;
    }

    /**
     * 税务凭据维护（P07-credentials）：账号/密码/授权范围与期限
     *
     * 密码留空表示保持原值；任何一项真正变化才 +1 版本号，便于追溯「凭据第几版」。
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCredential(Long memberId, Long enterpriseId, TaxCredentialUpdateRequest req) {
        Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId);
        if (enterprise == null) {
            throw new RuntimeException("主体不存在");
        }
        if (!memberId.equals(enterprise.getMemberId())) {
            throw new RuntimeException("无权修改该主体");
        }
        if (req == null) {
            throw new ServiceException("参数缺失：请求体不能为空");
        }

        TaxCredential query = new TaxCredential();
        query.setEnterpriseId(enterpriseId);
        List<TaxCredential> list = credentialService.selectTaxCredentialList(query);
        TaxCredential credential = (list != null && !list.isEmpty()) ? list.get(0) : null;
        if (credential == null) {
            throw new RuntimeException("该主体尚未采集税务凭据，请先提交认证资料");
        }

        boolean changed = false;
        if (StringUtils.isNotEmpty(req.getTaxAccount())) {
            credential.setTaxAccount(req.getTaxAccount().trim());
            changed = true;
        }
        if (StringUtils.isNotEmpty(req.getTaxPassword())) {
            credential.setTaxPassword(cryptoUtils.encrypt(req.getTaxPassword()));
            credential.setSecretKeyVersion(cryptoUtils.keyVersion());
            changed = true;
        }
        if (StringUtils.isNotEmpty(req.getAuthScope())) {
            credential.setAuthScope(req.getAuthScope().trim());
            changed = true;
        }
        if (req.getAuthStartDate() != null) {
            credential.setAuthStartDate(req.getAuthStartDate());
            changed = true;
        }
        if (req.getAuthEndDate() != null) {
            credential.setAuthEndDate(req.getAuthEndDate());
            changed = true;
        }
        if (!changed) {
            throw new ServiceException("参数缺失：没有需要更新的内容（账号/密码/授权至少传一项）");
        }

        long version = credential.getCredentialVersion() == null ? 1L : credential.getCredentialVersion() + 1;
        credential.setCredentialVersion(version);
        credentialService.updateTaxCredential(credential);
    }

    /**
     * 账号脱敏：手机号前3后4，其他保留首尾
     */
    private String maskAccount(String account) {
        if (StringUtils.isEmpty(account)) {
            return account;
        }
        if (account.length() <= 4) {
            return "****";
        }
        if (account.length() == 11) {
            return account.substring(0, 3) + "****" + account.substring(7);
        }
        return account.substring(0, 2) + "****" + account.substring(account.length() - 2);
    }
}
