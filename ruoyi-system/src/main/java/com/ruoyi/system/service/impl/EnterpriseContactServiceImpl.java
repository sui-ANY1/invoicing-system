package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EnterpriseContactMapper;
import com.ruoyi.system.domain.EnterpriseContact;
import com.ruoyi.system.service.IEnterpriseContactService;

/**
 * 企业经办人Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class EnterpriseContactServiceImpl implements IEnterpriseContactService 
{
    @Autowired
    private EnterpriseContactMapper enterpriseContactMapper;

    /**
     * 查询企业经办人
     * 
     * @param contactId 企业经办人主键
     * @return 企业经办人
     */
    @Override
    public EnterpriseContact selectEnterpriseContactByContactId(Long contactId)
    {
        return enterpriseContactMapper.selectEnterpriseContactByContactId(contactId);
    }

    /**
     * 查询企业经办人列表
     * 
     * @param enterpriseContact 企业经办人
     * @return 企业经办人
     */
    @Override
    public List<EnterpriseContact> selectEnterpriseContactList(EnterpriseContact enterpriseContact)
    {
        return enterpriseContactMapper.selectEnterpriseContactList(enterpriseContact);
    }

    /**
     * 新增企业经办人
     * 
     * @param enterpriseContact 企业经办人
     * @return 结果
     */
    @Override
    public int insertEnterpriseContact(EnterpriseContact enterpriseContact)
    {
        enterpriseContact.setCreateTime(DateUtils.getNowDate());
        return enterpriseContactMapper.insertEnterpriseContact(enterpriseContact);
    }

    /**
     * 修改企业经办人
     * 
     * @param enterpriseContact 企业经办人
     * @return 结果
     */
    @Override
    public int updateEnterpriseContact(EnterpriseContact enterpriseContact)
    {
        enterpriseContact.setUpdateTime(DateUtils.getNowDate());
        return enterpriseContactMapper.updateEnterpriseContact(enterpriseContact);
    }

    /**
     * 批量删除企业经办人
     * 
     * @param contactIds 需要删除的企业经办人主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseContactByContactIds(Long[] contactIds)
    {
        return enterpriseContactMapper.deleteEnterpriseContactByContactIds(contactIds);
    }

    /**
     * 删除企业经办人信息
     * 
     * @param contactId 企业经办人主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseContactByContactId(Long contactId)
    {
        return enterpriseContactMapper.deleteEnterpriseContactByContactId(contactId);
    }
}
