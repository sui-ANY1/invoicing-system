package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Enterprise;

/**
 * 开票企业Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IEnterpriseService 
{
    /**
     * 查询开票企业
     * 
     * @param enterpriseId 开票企业主键
     * @return 开票企业
     */
    public Enterprise selectEnterpriseByEnterpriseId(Long enterpriseId);

    /**
     * 查询开票企业列表
     * 
     * @param enterprise 开票企业
     * @return 开票企业集合
     */
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise);

    /**
     * 新增开票企业
     * 
     * @param enterprise 开票企业
     * @return 结果
     */
    public int insertEnterprise(Enterprise enterprise);

    /**
     * 修改开票企业
     * 
     * @param enterprise 开票企业
     * @return 结果
     */
    public int updateEnterprise(Enterprise enterprise);

    /**
     * 批量删除开票企业
     * 
     * @param enterpriseIds 需要删除的开票企业主键集合
     * @return 结果
     */
    public int deleteEnterpriseByEnterpriseIds(Long[] enterpriseIds);

    /**
     * 删除开票企业信息
     * 
     * @param enterpriseId 开票企业主键
     * @return 结果
     */
    public int deleteEnterpriseByEnterpriseId(Long enterpriseId);
}
