package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Attachment;

/**
 * 统一附件Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IAttachmentService 
{
    /**
     * 查询统一附件
     * 
     * @param attachmentId 统一附件主键
     * @return 统一附件
     */
    public Attachment selectAttachmentByAttachmentId(Long attachmentId);

    /**
     * 查询统一附件列表
     * 
     * @param attachment 统一附件
     * @return 统一附件集合
     */
    public List<Attachment> selectAttachmentList(Attachment attachment);

    /**
     * 新增统一附件
     * 
     * @param attachment 统一附件
     * @return 结果
     */
    public int insertAttachment(Attachment attachment);

    /**
     * 修改统一附件
     * 
     * @param attachment 统一附件
     * @return 结果
     */
    public int updateAttachment(Attachment attachment);

    /**
     * 批量删除统一附件
     * 
     * @param attachmentIds 需要删除的统一附件主键集合
     * @return 结果
     */
    public int deleteAttachmentByAttachmentIds(Long[] attachmentIds);

    /**
     * 删除统一附件信息
     * 
     * @param attachmentId 统一附件主键
     * @return 结果
     */
    public int deleteAttachmentByAttachmentId(Long attachmentId);
}
