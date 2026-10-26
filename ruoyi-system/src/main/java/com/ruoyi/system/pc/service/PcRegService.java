package com.ruoyi.system.pc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.domain.RegCompany;
import com.ruoyi.system.domain.RegShareholder;
import com.ruoyi.system.domain.RegSole;
import com.ruoyi.system.pc.support.PcAttachments;
import com.ruoyi.system.pc.support.PcDict;
import com.ruoyi.system.pc.vo.PcAttachmentVO;
import com.ruoyi.system.pc.vo.PcRegDetailVO;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.system.service.IRegApplyService;
import com.ruoyi.system.service.IRegCompanyService;
import com.ruoyi.system.service.IRegShareholderService;
import com.ruoyi.system.service.IRegSoleService;

/**
 * PC 注册信息（D11/D12）
 *
 * AC11/AC12：只读。只有列表、详情与受权附件预览，不提供任何写、审核、补件、导出、办结能力；
 * 注册资料与开票认证域隔离，PC 侧也不允许通过拼接请求改动注册资料。
 */
@Service
public class PcRegService {

    private static final String BIZ_REG_APPLY = "reg_apply";

    @Autowired
    private IRegApplyService regApplyService;

    @Autowired
    private IRegCompanyService regCompanyService;

    @Autowired
    private IRegShareholderService regShareholderService;

    @Autowired
    private IRegSoleService regSoleService;

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 注册信息分页列表（分页由 Controller 控制；空状态无新建入口）
     */
    public List<RegApply> list(RegApply query) {
        return regApplyService.selectRegApplyList(query);
    }

    /**
     * 注册详情（只读）：主单 + 公司资料（含股东）或个体户资料 + 已提交材料
     */
    public PcRegDetailVO detail(Long regApplyId) {
        if (regApplyId == null) {
            throw new ServiceException("参数缺失：regApplyId");
        }
        RegApply regApply = regApplyService.selectRegApplyByRegApplyId(regApplyId);
        if (regApply == null) {
            throw new ServiceException("注册申请不存在");
        }

        PcRegDetailVO vo = new PcRegDetailVO();
        vo.setRegApply(regApply);
        vo.setSubjectTypeText(PcDict.text(PcDict.SUBJECT_TYPE, regApply.getSubjectType()));
        vo.setCompleteStatusText(PcDict.text(PcDict.REG_COMPLETE_STATUS, regApply.getCompleteStatus()));

        if ("1".equals(regApply.getSubjectType())) {
            RegCompany companyQuery = new RegCompany();
            companyQuery.setRegApplyId(regApplyId);
            List<RegCompany> companies = regCompanyService.selectRegCompanyList(companyQuery);
            vo.setCompany(companies == null || companies.isEmpty() ? null : companies.get(0));

            RegShareholder shareholderQuery = new RegShareholder();
            shareholderQuery.setRegApplyId(regApplyId);
            vo.setShareholders(regShareholderService.selectRegShareholderList(shareholderQuery));
        } else if ("2".equals(regApply.getSubjectType())) {
            RegSole soleQuery = new RegSole();
            soleQuery.setRegApplyId(regApplyId);
            List<RegSole> soles = regSoleService.selectRegSoleList(soleQuery);
            vo.setSole(soles == null || soles.isEmpty() ? null : soles.get(0));
        }

        vo.setMaterials(materials(regApplyId));
        return vo;
    }

    /**
     * 已提交材料清单（受权附件预览，返回 attachmentId 供受控取流）
     */
    public List<PcAttachmentVO> materials(Long regApplyId) {
        Attachment query = new Attachment();
        query.setBizType(BIZ_REG_APPLY);
        query.setBizId(regApplyId);
        query.setDelFlag("0");
        return PcAttachments.toVOList(attachmentService.selectAttachmentList(query));
    }
}
