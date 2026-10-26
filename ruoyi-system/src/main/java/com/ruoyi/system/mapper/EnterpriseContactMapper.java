package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.EnterpriseContact;

/**
 * 企业经办人Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface EnterpriseContactMapper 
{
    /**
     * 查询企业经办人
     * 
     * @param contactId 企业经办人主键
     * @return 企业经办人
     */
    public EnterpriseContact selectEnterpriseContactByContactId(Long contactId);

    /**
     * 查询企业经办人列表
     * 
     * @param enterpriseContact 企业经办人
     * @return 企业经办人集合
     */
    public List<EnterpriseContact> selectEnterpriseContactList(EnterpriseContact enterpriseContact);

    /**
     * 新增企业经办人
     * 
     * @param enterpriseContact 企业经办人
     * @return 结果
     */
    public int insertEnterpriseContact(EnterpriseContact enterpriseContact);

    /**
     * 修改企业经办人
     * 
     * @param enterpriseContact 企业经办人
     * @return 结果
     */
    public int updateEnterpriseContact(EnterpriseContact enterpriseContact);

    /**
     * 删除企业经办人
     * 
     * @param contactId 企业经办人主键
     * @return 结果
     */
    public int deleteEnterpriseContactByContactId(Long contactId);

    /**
     * 批量删除企业经办人
     * 
     * @param contactIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEnterpriseContactByContactIds(Long[] contactIds);
}
