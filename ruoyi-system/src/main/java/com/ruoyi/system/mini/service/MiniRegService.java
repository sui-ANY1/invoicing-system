package com.ruoyi.system.mini.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.domain.RegCompany;
import com.ruoyi.system.domain.RegShareholder;
import com.ruoyi.system.domain.RegSole;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.request.RegApplyRequest;
import com.ruoyi.system.mini.dto.vo.MaterialVO;
import com.ruoyi.system.mini.dto.vo.RegApplyVO;
import com.ruoyi.system.mini.dto.vo.RegCompanyVO;
import com.ruoyi.system.mini.dto.vo.RegDetailVO;
import com.ruoyi.system.mini.dto.vo.RegShareholderVO;
import com.ruoyi.system.mini.dto.vo.RegSoleVO;
import com.ruoyi.system.service.IRegApplyService;
import com.ruoyi.system.service.IRegCompanyService;
import com.ruoyi.system.service.IRegShareholderService;
import com.ruoyi.system.service.IRegSoleService;

/**
 * 小程序注册收件业务编排
 */
@Service
public class MiniRegService {

    @Autowired
    private IRegApplyService applyService;
    @Autowired
    private IRegCompanyService companyService;
    @Autowired
    private IRegShareholderService shareholderService;
    @Autowired
    private IRegSoleService soleService;
    @Autowired
    private MiniAttachService attachService;

    /**
     * 是否强制要求上传必传材料（默认关闭，前端接入材料上传后再打开；打开后公司/个体必传证件照片）
     */
    @Value("${mini.reg.require-materials:false}")
    private boolean requireMaterials;

    /**
     * 提交注册收件（主单 + 公司/个体户 + 股东 + 材料附件）
     *
     * <p>接口为“可选鉴权”：登录用户提交时绑定 memberId，匿名提交允许但归属为空，此后无法在 P24 回看。</p>
     *
     * @param request 提交内容
     * @param memberId 当前登录会员 id，未登录为 null
     */
    @Transactional(rollbackFor = Exception.class)
    public Long apply(RegApplyRequest request, Long memberId) {
        if (request == null || request.getApply() == null) {
            throw new ServiceException("注册信息不能为空");
        }
        RegApplyRequest.RegApplyForm form = request.getApply();
        if (StringUtils.isEmpty(form.getContactName())) {
            throw new ServiceException("联系人姓名不能为空");
        }
        String phone = form.getContactPhone();
        if (StringUtils.isEmpty(phone) || !phone.matches("^1\\d{10}$")) {
            throw new ServiceException("联系人手机号格式不正确");
        }
        String subjectType = form.getSubjectType();
        if (!"1".equals(subjectType) && !"2".equals(subjectType)) {
            throw new ServiceException("主体类型不能为空（1有限责任公司 2个体工商户）");
        }
        if ("1".equals(subjectType) && request.getCompany() == null) {
            throw new ServiceException("公司主体请填写公司注册资料");
        }
        if ("2".equals(subjectType) && request.getSole() == null) {
            throw new ServiceException("个体工商户请填写个体户注册资料");
        }

        // 公司资料必填项（需求 REG-C01~C05）
        if ("1".equals(subjectType)) {
            RegApplyRequest.CompanyForm cf = request.getCompany();
            if (StringUtils.isEmpty(cf.getProposedName())) {
                throw new ServiceException("拟用公司名称不能为空");
            }
            if (cf.getRegisteredCapital() == null || cf.getRegisteredCapital().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException("注册资本必须是大于 0 的数值（万元）");
            }
            if (StringUtils.isEmpty(cf.getBusinessScope())) {
                throw new ServiceException("经营范围不能为空");
            }
            if (StringUtils.isEmpty(cf.getLegalPersonName())) {
                throw new ServiceException("法定代表人为姓名不能为空");
            }
            if (StringUtils.isEmpty(cf.getLegalPersonIdType())) {
                throw new ServiceException("法人证件类型不能为空（1居民身份证 2其他）");
            }
            if (StringUtils.isEmpty(cf.getLegalPersonIdCard())) {
                throw new ServiceException("法人证件号码不能为空");
            }
            if (StringUtils.isEmpty(cf.getLegalPersonPhone()) || !cf.getLegalPersonPhone().matches("^1\\d{10}$")) {
                throw new ServiceException("法人本人手机号格式不正确（11 位，1 开头）");
            }
            if (request.getShareholders() == null || request.getShareholders().isEmpty()) {
                throw new ServiceException("至少填写一名股东（有限公司）");
            }
            BigDecimal ratioSum = BigDecimal.ZERO;
            for (RegApplyRequest.ShareholderForm sf : request.getShareholders()) {
                if (StringUtils.isEmpty(sf.getShareholderName())) {
                    throw new ServiceException("股东姓名/机构名称不能为空");
                }
                if (sf.getHoldRatio() == null || sf.getHoldRatio().compareTo(BigDecimal.ZERO) < 0) {
                    throw new ServiceException("股东持股比例不能为空且不能为负");
                }
                ratioSum = ratioSum.add(sf.getHoldRatio());
            }
            if (ratioSum.subtract(new BigDecimal("100")).abs().compareTo(new BigDecimal("0.01")) > 0) {
                throw new ServiceException("股东持股比例合计必须为 100（当前 " + ratioSum.stripTrailingZeros().toPlainString() + "）");
            }
        }

        // 个体户资料必填项（需求 REG 个体户分节；名称允许为空）
        if ("2".equals(subjectType)) {
            RegApplyRequest.SoleForm sf = request.getSole();
            if (StringUtils.isEmpty(sf.getOperatorName())) {
                throw new ServiceException("经营者姓名不能为空");
            }
            if (StringUtils.isEmpty(sf.getOperatorIdType())) {
                throw new ServiceException("证件类型不能为空（1居民身份证 2其他）");
            }
            if (StringUtils.isEmpty(sf.getOperatorIdCard())) {
                throw new ServiceException("证件号码不能为空");
            }
            if (StringUtils.isEmpty(sf.getOperatorPhone()) || !sf.getOperatorPhone().matches("^1\\d{10}$")) {
                throw new ServiceException("经营者手机号格式不正确（11 位，1 开头）");
            }
            if (StringUtils.isEmpty(sf.getBusinessScope())) {
                throw new ServiceException("经营范围不能为空");
            }
        }
        RegApply reg = new RegApply();
        reg.setSubjectType(subjectType);
        reg.setContactName(form.getContactName());
        reg.setContactPhone(phone);
        reg.setMemberId(memberId);
        reg.setRegApplyNo(generateRegNo());
        reg.setCompleteStatus("0");
        reg.setDelFlag("0");
        applyService.insertRegApply(reg);
        Long regApplyId = reg.getRegApplyId();

        if ("1".equals(subjectType)) {
            RegApplyRequest.CompanyForm cf = request.getCompany();
            RegCompany company = new RegCompany();
            company.setRegApplyId(regApplyId);
            company.setProposedName(cf.getProposedName());
            company.setAlternateNames(cf.getAlternateNames());
            company.setRegisteredCapital(cf.getRegisteredCapital());
            company.setBusinessScope(cf.getBusinessScope());
            company.setLegalPersonName(cf.getLegalPersonName());
            company.setLegalPersonIdType(cf.getLegalPersonIdType());
            company.setLegalPersonIdCard(cf.getLegalPersonIdCard());
            company.setLegalPersonPhone(cf.getLegalPersonPhone());
            companyService.insertRegCompany(company);

            if (request.getShareholders() != null) {
                int sort = 1;
                for (RegApplyRequest.ShareholderForm sf : request.getShareholders()) {
                    RegShareholder sh = new RegShareholder();
                    sh.setRegApplyId(regApplyId);
                    sh.setShareholderType(sf.getShareholderType());
                    sh.setShareholderName(sf.getShareholderName());
                    sh.setContactPhone(sf.getContactPhone());
                    sh.setHoldRatio(sf.getHoldRatio());
                    sh.setQualificationUrl(sf.getQualificationUrl());
                    sh.setSortNo(sf.getSortNo() == null ? (long) sort : sf.getSortNo());
                    shareholderService.insertRegShareholder(sh);
                    attachService.bindUrl(memberId, MiniAttachConstants.BIZ_REG_APPLY, regApplyId,
                            MiniAttachConstants.ORG_SHAREHOLDER_QUALIFICATION, sf.getShareholderName(),
                            sf.getQualificationUrl());
                    sort++;
                }
            }
        } else {
            RegApplyRequest.SoleForm sf = request.getSole();
            RegSole sole = new RegSole();
            sole.setRegApplyId(regApplyId);
            sole.setHandleIdentity(sf.getHandleIdentity());
            sole.setOperatorName(sf.getOperatorName());
            sole.setOperatorIdType(sf.getOperatorIdType());
            sole.setOperatorIdCard(sf.getOperatorIdCard());
            sole.setOperatorPhone(sf.getOperatorPhone());
            sole.setOperatorAddress(sf.getOperatorAddress());
            sole.setBusinessForm(sf.getBusinessForm());
            sole.setFamilyMembers(sf.getFamilyMembers());
            sole.setProposedName(sf.getProposedName());
            sole.setBusinessScope(sf.getBusinessScope());
            sole.setPremisesType(sf.getPremisesType());
            sole.setPremisesAddress(sf.getPremisesAddress());
            soleService.insertRegSole(sole);
        }

        attachService.bind(memberId, MiniAttachConstants.BIZ_REG_APPLY, regApplyId, subjectType,
                request.getMaterials(), requireMaterials);
        return regApplyId;
    }

    /**
     * 注册历史：当前会员提交的收件单（带拟定名称，便于按公司名称搜索）
     */
    public List<RegApplyVO> history(Long memberId) {
        RegApply q = new RegApply();
        q.setMemberId(memberId);
        List<RegApply> list = applyService.selectRegApplyList(q);
        List<RegApplyVO> result = new ArrayList<>();
        if (list != null) {
            for (RegApply r : list) {
                RegApplyVO vo = toApplyVO(r);
                vo.setCompanyName(resolveProposedName(r.getRegApplyId()));
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 取拟定名称：公司取 t_reg_company.proposed_name，个体户取 t_reg_sole.proposed_name
     */
    private String resolveProposedName(Long regApplyId) {
        RegCompany cq = new RegCompany();
        cq.setRegApplyId(regApplyId);
        List<RegCompany> companies = companyService.selectRegCompanyList(cq);
        if (companies != null && !companies.isEmpty()) {
            return companies.get(0).getProposedName();
        }
        RegSole sq = new RegSole();
        sq.setRegApplyId(regApplyId);
        List<RegSole> soles = soleService.selectRegSoleList(sq);
        if (soles != null && !soles.isEmpty()) {
            return soles.get(0).getProposedName();
        }
        return null;
    }

    /**
     * 注册详情（主单 + 公司/个体户 + 股东）
     */
    public RegDetailVO detail(Long memberId, Long id) {
        RegApply reg = getOwnedReg(memberId, id);
        RegDetailVO result = new RegDetailVO();
        result.setApply(toApplyVO(reg));

        RegCompany cq = new RegCompany();
        cq.setRegApplyId(id);
        List<RegCompany> companies = companyService.selectRegCompanyList(cq);
        if (companies != null && !companies.isEmpty()) {
            result.setCompany(toCompanyVO(companies.get(0)));
        }

        RegShareholder sq = new RegShareholder();
        sq.setRegApplyId(id);
        List<RegShareholder> shs = shareholderService.selectRegShareholderList(sq);
        List<RegShareholderVO> shVOs = new ArrayList<>();
        if (shs != null) {
            for (RegShareholder s : shs) {
                shVOs.add(toShareholderVO(s));
            }
        }
        result.setShareholders(shVOs);

        RegSole eq = new RegSole();
        eq.setRegApplyId(id);
        List<RegSole> soles = soleService.selectRegSoleList(eq);
        if (soles != null && !soles.isEmpty()) {
            result.setSole(toSoleVO(soles.get(0)));
        }
        result.setMaterials(materials(id));
        return result;
    }

    /**
     * 注册材料清单（t_attachment 中 biz_type=reg_apply 且 biz_id=收件ID）
     */
    public List<MaterialVO> materials(Long regApplyId) {
        return attachService.list(MiniAttachConstants.BIZ_REG_APPLY, regApplyId);
    }

    /**
     * 取注册附件并校验归属（仅 reg_apply 类型）
     */
    public Attachment getOwnedAttachment(Long memberId, Long attachmentId) {
        Attachment att = attachService.getOwnedForMember(memberId, attachmentId);
        if (!MiniAttachConstants.BIZ_REG_APPLY.equals(att.getBizType())) {
            throw new RuntimeException("非注册收件文件");
        }
        return att;
    }

    private RegApply getOwnedReg(Long memberId, Long id) {
        RegApply reg = applyService.selectRegApplyByRegApplyId(id);
        if (reg == null) {
            throw new RuntimeException("注册申请不存在");
        }
        if (reg.getMemberId() == null) {
            throw new RuntimeException("该注册申请为未登录提交，小程序内无法查看，请联系客服核验收件编号");
        }
        if (!reg.getMemberId().equals(memberId)) {
            throw new RuntimeException("无权查看该注册申请");
        }
        return reg;
    }

    private RegApplyVO toApplyVO(RegApply r) {
        RegApplyVO vo = new RegApplyVO();
        vo.setId(r.getRegApplyId());
        vo.setRegApplyNo(r.getRegApplyNo());
        vo.setSubjectType(r.getSubjectType());
        vo.setContactName(r.getContactName());
        vo.setContactPhone(r.getContactPhone());
        vo.setCompleteStatus(r.getCompleteStatus());
        vo.setCompleteBy(r.getCompleteBy());
        vo.setCompleteTime(r.getCompleteTime());
        vo.setCompleteRemark(r.getCompleteRemark());
        vo.setCreateTime(r.getCreateTime());
        return vo;
    }

    private RegCompanyVO toCompanyVO(RegCompany c) {
        RegCompanyVO vo = new RegCompanyVO();
        vo.setId(c.getCompanyId());
        vo.setProposedName(c.getProposedName());
        vo.setAlternateNames(c.getAlternateNames());
        vo.setRegisteredCapital(c.getRegisteredCapital());
        vo.setBusinessScope(c.getBusinessScope());
        vo.setLegalPersonName(c.getLegalPersonName());
        vo.setLegalPersonIdType(c.getLegalPersonIdType());
        vo.setLegalPersonIdCard(c.getLegalPersonIdCard());
        vo.setLegalPersonPhone(c.getLegalPersonPhone());
        return vo;
    }

    private RegShareholderVO toShareholderVO(RegShareholder s) {
        RegShareholderVO vo = new RegShareholderVO();
        vo.setId(s.getShareholderId());
        vo.setShareholderType(s.getShareholderType());
        vo.setShareholderName(s.getShareholderName());
        vo.setContactPhone(s.getContactPhone());
        vo.setHoldRatio(s.getHoldRatio());
        vo.setQualificationUrl(s.getQualificationUrl());
        vo.setSortNo(s.getSortNo());
        return vo;
    }

    private RegSoleVO toSoleVO(RegSole s) {
        RegSoleVO vo = new RegSoleVO();
        vo.setId(s.getSoleId());
        vo.setHandleIdentity(s.getHandleIdentity());
        vo.setOperatorName(s.getOperatorName());
        vo.setOperatorIdType(s.getOperatorIdType());
        vo.setOperatorIdCard(s.getOperatorIdCard());
        vo.setOperatorPhone(s.getOperatorPhone());
        vo.setOperatorAddress(s.getOperatorAddress());
        vo.setBusinessForm(s.getBusinessForm());
        vo.setFamilyMembers(s.getFamilyMembers());
        vo.setProposedName(s.getProposedName());
        vo.setBusinessScope(s.getBusinessScope());
        vo.setPremisesType(s.getPremisesType());
        vo.setPremisesAddress(s.getPremisesAddress());
        return vo;
    }

    private String generateRegNo() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) (Math.random() * 9000) + 1000;
        return "REG" + date + random;
    }
}
