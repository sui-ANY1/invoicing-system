package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AttachmentMapper;
import com.ruoyi.system.domain.Attachment;
import com.ruoyi.system.service.IAttachmentService;

/**
 * 统一附件Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class AttachmentServiceImpl implements IAttachmentService 
{
    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 查询统一附件
     * 
     * @param attachmentId 统一附件主键
     * @return 统一附件
     */
    @Override
    public Attachment selectAttachmentByAttachmentId(Long attachmentId)
    {
        return attachmentMapper.selectAttachmentByAttachmentId(attachmentId);
    }

    /**
     * 查询统一附件列表
     * 
     * @param attachment 统一附件
     * @return 统一附件
     */
    @Override
    public List<Attachment> selectAttachmentList(Attachment attachment)
    {
        return attachmentMapper.selectAttachmentList(attachment);
    }

    /**
     * 新增统一附件
     * 
     * @param attachment 统一附件
     * @return 结果
     */
    @Override
    public int insertAttachment(Attachment attachment)
    {
        attachment.setCreateTime(DateUtils.getNowDate());
        return attachmentMapper.insertAttachment(attachment);
    }

    /**
     * 修改统一附件
     * 
     * @param attachment 统一附件
     * @return 结果
     */
    @Override
    public int updateAttachment(Attachment attachment)
    {
        return attachmentMapper.updateAttachment(attachment);
    }

    /**
     * 批量删除统一附件
     * 
     * @param attachmentIds 需要删除的统一附件主键
     * @return 结果
     */
    @Override
    public int deleteAttachmentByAttachmentIds(Long[] attachmentIds)
    {
        return attachmentMapper.deleteAttachmentByAttachmentIds(attachmentIds);
    }

    /**
     * 删除统一附件信息
     * 
     * @param attachmentId 统一附件主键
     * @return 结果
     */
    @Override
    public int deleteAttachmentByAttachmentId(Long attachmentId)
    {
        return attachmentMapper.deleteAttachmentByAttachmentId(attachmentId);
    }
}
