package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AuditRecord;

/**
 * 认证审核记录Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IAuditRecordService 
{
    /**
     * 查询认证审核记录
     * 
     * @param auditRecordId 认证审核记录主键
     * @return 认证审核记录
     */
    public AuditRecord selectAuditRecordByAuditRecordId(Long auditRecordId);

    /**
     * 查询认证审核记录列表
     * 
     * @param auditRecord 认证审核记录
     * @return 认证审核记录集合
     */
    public List<AuditRecord> selectAuditRecordList(AuditRecord auditRecord);

    /**
     * 新增认证审核记录
     * 
     * @param auditRecord 认证审核记录
     * @return 结果
     */
    public int insertAuditRecord(AuditRecord auditRecord);

    /**
     * 修改认证审核记录
     * 
     * @param auditRecord 认证审核记录
     * @return 结果
     */
    public int updateAuditRecord(AuditRecord auditRecord);

    /**
     * 批量删除认证审核记录
     * 
     * @param auditRecordIds 需要删除的认证审核记录主键集合
     * @return 结果
     */
    public int deleteAuditRecordByAuditRecordIds(Long[] auditRecordIds);

    /**
     * 删除认证审核记录信息
     * 
     * @param auditRecordId 认证审核记录主键
     * @return 结果
     */
    public int deleteAuditRecordByAuditRecordId(Long auditRecordId);
}
