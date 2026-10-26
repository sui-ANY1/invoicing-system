package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AuditRecordMapper;
import com.ruoyi.system.domain.AuditRecord;
import com.ruoyi.system.service.IAuditRecordService;

/**
 * 认证审核记录Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class AuditRecordServiceImpl implements IAuditRecordService 
{
    @Autowired
    private AuditRecordMapper auditRecordMapper;

    /**
     * 查询认证审核记录
     * 
     * @param auditRecordId 认证审核记录主键
     * @return 认证审核记录
     */
    @Override
    public AuditRecord selectAuditRecordByAuditRecordId(Long auditRecordId)
    {
        return auditRecordMapper.selectAuditRecordByAuditRecordId(auditRecordId);
    }

    /**
     * 查询认证审核记录列表
     * 
     * @param auditRecord 认证审核记录
     * @return 认证审核记录
     */
    @Override
    public List<AuditRecord> selectAuditRecordList(AuditRecord auditRecord)
    {
        return auditRecordMapper.selectAuditRecordList(auditRecord);
    }

    /**
     * 新增认证审核记录
     * 
     * @param auditRecord 认证审核记录
     * @return 结果
     */
    @Override
    public int insertAuditRecord(AuditRecord auditRecord)
    {
        auditRecord.setCreateTime(DateUtils.getNowDate());
        return auditRecordMapper.insertAuditRecord(auditRecord);
    }

    /**
     * 修改认证审核记录
     * 
     * @param auditRecord 认证审核记录
     * @return 结果
     */
    @Override
    public int updateAuditRecord(AuditRecord auditRecord)
    {
        return auditRecordMapper.updateAuditRecord(auditRecord);
    }

    /**
     * 批量删除认证审核记录
     * 
     * @param auditRecordIds 需要删除的认证审核记录主键
     * @return 结果
     */
    @Override
    public int deleteAuditRecordByAuditRecordIds(Long[] auditRecordIds)
    {
        return auditRecordMapper.deleteAuditRecordByAuditRecordIds(auditRecordIds);
    }

    /**
     * 删除认证审核记录信息
     * 
     * @param auditRecordId 认证审核记录主键
     * @return 结果
     */
    @Override
    public int deleteAuditRecordByAuditRecordId(Long auditRecordId)
    {
        return auditRecordMapper.deleteAuditRecordByAuditRecordId(auditRecordId);
    }
}
