package com.ruoyi.system.mini.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.domain.RegApply;
import com.ruoyi.system.mini.constant.MiniAttachConstants;
import com.ruoyi.system.mini.dto.request.MaterialForm;
import com.ruoyi.system.mini.dto.vo.MaterialVO;
import com.ruoyi.system.service.IAttachmentService;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IRedApplyService;
import com.ruoyi.system.service.IRegApplyService;

/**
 * 小程序附件（材料）登记与受控读取
 *
 * <p>上传接口先把文件登记为 biz_type=temp、biz_id=0 的临时附件；业务提交时由本服务把附件回填到
 * 具体业务（reg_apply / enterprise / invoice_apply / red_apply），使材料类型、关联对象与业务归属可查，
 * 并统一承担“材料预览”的归属校验。</p>
 *
 * @author sui-ANY1
 */
@Service
public class MiniAttachService {

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IRegApplyService regApplyService;

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private IInvoiceApplyService invoiceApplyService;

    @Autowired
    private IRedApplyService redApplyService;

    /**
     * 绑定显式材料清单（不带必传校验）
     */
    public void bind(Long memberId, String bizType, Long bizId, String subjectType, List<MaterialForm> materials) {
        bind(memberId, bizType, bizId, subjectType, materials, false);
    }

    /**
     * 绑定显式材料清单
     *
     * @param memberId 当前会员（未登录为 null）
     * @param bizType 业务类型
     * @param bizId 业务主键
     * @param subjectType 主体类型（注册收件用于区分公司/个体户允许的材料）
     * @param materials 材料清单，可为空
     * @param require 是否强制要求必传材料（当前仅注册收件有必传定义）
     */
    public void bind(Long memberId, String bizType, Long bizId, String subjectType, List<MaterialForm> materials,
            boolean require) {
        List<MaterialForm> normalized = normalize(bizType, subjectType, materials, require);
        for (MaterialForm m : normalized) {
            Attachment target = findUploaded(memberId, m);
            if (target != null) {
                requireOwned(memberId, target);
                boolean sameBiz = bizType.equals(target.getBizType()) && bizId.equals(target.getBizId());
                boolean bound = target.getBizId() != null && target.getBizId() != 0L;
                if (bound && !sameBiz) {
                    // 同一文件被另一业务占用：为本业务另立一条记录，保证每条业务的材料清单自洽
                    insertNew(memberId, bizType, bizId, m.getMaterialType(),
                            StringUtils.isNotEmpty(m.getRefKey()) ? m.getRefKey() : null, m.getFileUrl(),
                            StringUtils.isNotEmpty(m.getFileName()) ? m.getFileName() : null);
                    continue;
                }
                if (sameBiz && bound && m.getMaterialType().equals(target.getMaterialType())
                        && StringUtils.equals(StringUtils.trimToEmpty(m.getRefKey()),
                                StringUtils.trimToEmpty(target.getRefKey()))) {
                    // 同一业务下完全相同的登记，幂等跳过
                    continue;
                }
                Attachment update = new Attachment();
                update.setAttachmentId(target.getAttachmentId());
                update.setBizType(bizType);
                update.setBizId(bizId);
                update.setMaterialType(m.getMaterialType());
                update.setRefKey(StringUtils.isNotEmpty(m.getRefKey()) ? m.getRefKey() : null);
                if (StringUtils.isNotEmpty(m.getFileName())) {
                    update.setFileName(m.getFileName());
                }
                update.setUploadBy(memberId == null ? target.getUploadBy() : String.valueOf(memberId));
                attachmentService.updateAttachment(update);
                continue;
            }
            requireUrlUsable(memberId, m.getFileUrl());
            insertNew(memberId, bizType, bizId, m.getMaterialType(),
                    StringUtils.isNotEmpty(m.getRefKey()) ? m.getRefKey() : null, m.getFileUrl(),
                    StringUtils.isNotEmpty(m.getFileName()) ? m.getFileName() : null);
        }
    }

    /**
     * 按 URL 绑定单个材料（用于既有请求体里的附件字段，如营业执照、身份证、交易证明）
     *
     * <p>幂等：同一业务下同一 URL 已登记则跳过；上传阶段已登记过则改为回填业务归属。</p>
     */
    public void bindUrl(Long memberId, String bizType, Long bizId, String materialType, String refKey, String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        if (!isUploadedPath(fileUrl)) {
            throw new RuntimeException("附件地址不合法，请使用上传接口返回的地址");
        }
        requireUrlUsable(memberId, fileUrl);
        Attachment existed = findBound(bizType, bizId, fileUrl);
        if (existed != null) {
            requireOwned(memberId, existed);
            return;
        }
        Attachment uploaded = findUploadedByUrl(memberId, fileUrl);
        if (uploaded != null) {
            requireOwned(memberId, uploaded);
            Attachment update = new Attachment();
            update.setAttachmentId(uploaded.getAttachmentId());
            update.setBizType(bizType);
            update.setBizId(bizId);
            update.setMaterialType(materialType);
            update.setRefKey(StringUtils.isNotEmpty(refKey) ? refKey : uploaded.getRefKey());
            update.setUploadBy(memberId == null ? uploaded.getUploadBy() : String.valueOf(memberId));
            attachmentService.updateAttachment(update);
            return;
        }
        insertNew(memberId, bizType, bizId, materialType, refKey, fileUrl, null);
    }

    /**
     * 业务下的材料清单（按附件ID升序）
     */
    public List<MaterialVO> list(String bizType, Long bizId) {
        Attachment q = new Attachment();
        q.setBizType(bizType);
        q.setBizId(bizId);
        q.setDelFlag("0");
        List<Attachment> list = attachmentService.selectAttachmentList(q);
        List<MaterialVO> result = new ArrayList<>();
        if (list != null) {
            for (Attachment att : list) {
                result.add(toVO(att));
            }
        }
        return result;
    }

    /**
     * 取附件并校验当前会员是否有权读取（上传人本人，或所属业务归本人）
     */
    public Attachment getOwnedForMember(Long memberId, Long attachmentId) {
        if (memberId == null) {
            throw new RuntimeException("请先登录");
        }
        Attachment att = attachmentService.selectAttachmentByAttachmentId(attachmentId);
        if (att == null) {
            throw new RuntimeException("文件不存在");
        }
        if (memberId.toString().equals(att.getUploadBy())) {
            return att;
        }
        Long ownerId = bizOwner(att.getBizType(), att.getBizId());
        if (ownerId != null && ownerId.equals(memberId)) {
            return att;
        }
        throw new RuntimeException("无权查看该文件");
    }

    /**
     * 业务归属会员 id（无法判定时返回 null）
     */
    private Long bizOwner(String bizType, Long bizId) {
        if (bizType == null || bizId == null) {
            return null;
        }
        if (MiniAttachConstants.BIZ_REG_APPLY.equals(bizType)) {
            RegApply reg = regApplyService.selectRegApplyByRegApplyId(bizId);
            return reg == null ? null : reg.getMemberId();
        }
        if (MiniAttachConstants.BIZ_ENTERPRISE.equals(bizType)) {
            Enterprise e = enterpriseService.selectEnterpriseByEnterpriseId(bizId);
            return e == null ? null : e.getMemberId();
        }
        if (MiniAttachConstants.BIZ_INVOICE_APPLY.equals(bizType)) {
            InvoiceApply a = invoiceApplyService.selectInvoiceApplyByApplyId(bizId);
            return a == null ? null : a.getMemberId();
        }
        if (MiniAttachConstants.BIZ_RED_APPLY.equals(bizType)) {
            RedApply r = redApplyService.selectRedApplyByRedApplyId(bizId);
            return r == null ? null : r.getMemberId();
        }
        return null;
    }

    /**
     * 校验并去重材料清单（按材料类型 + 关联对象键 + 文件地址去重）
     */
    private List<MaterialForm> normalize(String bizType, String subjectType, List<MaterialForm> materials,
            boolean require) {
        List<MaterialForm> result = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        Set<String> types = new HashSet<>();
        if (materials != null) {
            for (MaterialForm m : materials) {
                if (m == null || StringUtils.isEmpty(m.getMaterialType())) {
                    continue;
                }
                if (!MiniAttachConstants.isAllowedMaterial(bizType, subjectType, m.getMaterialType())) {
                    throw new RuntimeException("材料类型不适用于当前业务：" + m.getMaterialType());
                }
                if (StringUtils.isEmpty(m.getFileUrl()) && m.getAttachmentId() == null) {
                    throw new RuntimeException("材料缺少文件：请先调用上传接口取得 url 或 attachmentId");
                }
                String key = m.getMaterialType() + "|" + StringUtils.trimToEmpty(m.getRefKey()) + "|"
                        + StringUtils.trimToEmpty(m.getFileUrl());
                if (seen.add(key)) {
                    result.add(m);
                    types.add(m.getMaterialType());
                }
            }
        }
        if (require && MiniAttachConstants.BIZ_REG_APPLY.equals(bizType)) {
            for (String required : MiniAttachConstants.requiredRegMaterials(subjectType)) {
                if (!types.contains(required)) {
                    throw new RuntimeException("请上传必传材料：" + required);
                }
            }
        }
        return result;
    }

    private Attachment findUploaded(Long memberId, MaterialForm m) {
        if (m.getAttachmentId() != null) {
            Attachment att = attachmentService.selectAttachmentByAttachmentId(m.getAttachmentId());
            if (att == null) {
                throw new RuntimeException("附件不存在：" + m.getAttachmentId());
            }
            return att;
        }
        return StringUtils.isEmpty(m.getFileUrl()) ? null : findUploadedByUrl(memberId, m.getFileUrl());
    }

    /**
     * 找上传阶段登记的附件行：优先返回本人上传的临时行（biz_id 为 0）
     */
    private Attachment findUploadedByUrl(Long memberId, String fileUrl) {
        Attachment q = new Attachment();
        q.setFileUrl(fileUrl);
        q.setDelFlag("0");
        List<Attachment> list = attachmentService.selectAttachmentList(q);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Attachment fallback = null;
        for (Attachment att : list) {
            if (att.getBizId() != null && att.getBizId() != 0L) {
                continue;
            }
            if (fallback == null) {
                fallback = att;
            }
            if (owned(memberId, att)) {
                return att;
            }
        }
        return fallback;
    }

    /**
     * 附件是否可由当前会员使用：本人上传、业务归本人，或上传阶段未登录（upload_by 为空）
     */
    private boolean owned(Long memberId, Attachment att) {
        if (memberId == null) {
            return true;
        }
        String by = att.getUploadBy();
        if (StringUtils.isEmpty(by) || memberId.toString().equals(by)) {
            return true;
        }
        return att.getBizId() != null && att.getBizId() != 0L && memberId.equals(bizOwner(att.getBizType(), att.getBizId()));
    }

    private void requireOwned(Long memberId, Attachment att) {
        if (!owned(memberId, att)) {
            throw new RuntimeException("无权使用该附件，请重新上传：" + att.getAttachmentId());
        }
    }

    /**
     * 该 URL 若已被他人登记过，则不允许当前会员直接引用（防止拿别人的文件充当自己的材料）
     */
    private void requireUrlUsable(Long memberId, String fileUrl) {
        if (memberId == null) {
            return;
        }
        Attachment q = new Attachment();
        q.setFileUrl(fileUrl);
        q.setDelFlag("0");
        List<Attachment> list = attachmentService.selectAttachmentList(q);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Attachment att : list) {
            if (owned(memberId, att)) {
                return;
            }
        }
        throw new RuntimeException("无权使用该附件，请重新上传");
    }

    private Attachment findBound(String bizType, Long bizId, String fileUrl) {
        Attachment q = new Attachment();
        q.setBizType(bizType);
        q.setBizId(bizId);
        q.setFileUrl(fileUrl);
        q.setDelFlag("0");
        List<Attachment> list = attachmentService.selectAttachmentList(q);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private void insertNew(Long memberId, String bizType, Long bizId, String materialType, String refKey,
            String fileUrl, String fileName) {
        if (!isUploadedPath(fileUrl)) {
            throw new RuntimeException("附件地址不合法，请使用上传接口返回的地址");
        }
        Attachment insert = new Attachment();
        insert.setBizType(bizType);
        insert.setBizId(bizId);
        insert.setMaterialType(materialType);
        insert.setRefKey(refKey);
        insert.setFileUrl(fileUrl);
        insert.setFileName(StringUtils.isNotEmpty(fileName) ? fileName : fileNameOf(fileUrl));
        insert.setFileType(extensionOf(fileUrl));
        insert.setUploadBy(memberId == null ? "" : String.valueOf(memberId));
        insert.setDelFlag("0");
        insert.setCreateTime(new Date());
        attachmentService.insertAttachment(insert);
    }

    /**
     * 上传接口返回的相对访问路径（/profile/...）
     */
    private boolean isUploadedPath(String fileUrl) {
        return StringUtils.isNotEmpty(fileUrl) && fileUrl.startsWith("/profile/");
    }

    private String fileNameOf(String fileUrl) {
        int idx = fileUrl.lastIndexOf('/');
        return idx >= 0 ? fileUrl.substring(idx + 1) : fileUrl;
    }

    private String extensionOf(String fileUrl) {
        int idx = fileUrl.lastIndexOf('.');
        return idx >= 0 ? fileUrl.substring(idx + 1).toLowerCase() : null;
    }

    private MaterialVO toVO(Attachment att) {
        MaterialVO vo = new MaterialVO();
        vo.setAttachmentId(att.getAttachmentId());
        vo.setMaterialType(att.getMaterialType());
        vo.setRefKey(att.getRefKey());
        vo.setFileName(att.getFileName());
        vo.setFileUrl(att.getFileUrl());
        vo.setFileType(att.getFileType());
        vo.setFileSize(att.getFileSize());
        vo.setCreateTime(att.getCreateTime());
        return vo;
    }
}
