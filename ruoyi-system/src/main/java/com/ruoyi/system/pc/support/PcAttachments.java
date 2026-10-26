package com.ruoyi.system.pc.support;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.pc.vo.PcAttachmentVO;

/**
 * 附件展示转换
 *
 * 安全口径：PC 响应里不回传 /profile 相对地址（该路径由 RuoYi 静态资源映射直接暴露），
 * 前端一律用 attachmentId 调受控接口 POST /pc/file/attachment/download 取流（AC28/§10.1）。
 */
public final class PcAttachments {

    private PcAttachments() {
    }

    public static PcAttachmentVO toVO(Attachment attachment) {
        if (attachment == null) {
            return null;
        }
        PcAttachmentVO vo = new PcAttachmentVO();
        vo.setAttachmentId(attachment.getAttachmentId());
        vo.setBizType(attachment.getBizType());
        vo.setBizId(attachment.getBizId());
        vo.setMaterialType(attachment.getMaterialType());
        vo.setMaterialName(materialName(attachment.getMaterialType()));
        vo.setRefKey(attachment.getRefKey());
        vo.setFileType(attachment.getFileType());
        vo.setFileName(attachment.getFileName());
        vo.setFileSize(attachment.getFileSize());
        vo.setUploadBy(attachment.getUploadBy());
        vo.setCreateTime(attachment.getCreateTime());
        // 受控访问：不暴露平台内文件路径
        vo.setFileUrl(null);
        return vo;
    }

    public static List<PcAttachmentVO> toVOList(List<Attachment> attachments) {
        List<PcAttachmentVO> list = new ArrayList<>();
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                list.add(toVO(attachment));
            }
        }
        return list;
    }

    public static String materialName(String materialType) {
        if (materialType == null || materialType.isEmpty()) {
            return null;
        }
        return PcDict.text(PcDict.MATERIAL, materialType);
    }
}
