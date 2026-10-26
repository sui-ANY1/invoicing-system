package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EnterpriseMapper;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.service.IEnterpriseService;

/**
 * 开票企业Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class EnterpriseServiceImpl implements IEnterpriseService 
{
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    /**
     * 查询开票企业
     * 
     * @param enterpriseId 开票企业主键
     * @return 开票企业
     */
    @Override
    public Enterprise selectEnterpriseByEnterpriseId(Long enterpriseId)
    {
        return enterpriseMapper.selectEnterpriseByEnterpriseId(enterpriseId);
    }

    /**
     * 查询开票企业列表
     * 
     * @param enterprise 开票企业
     * @return 开票企业
     */
    @Override
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise)
    {
        return enterpriseMapper.selectEnterpriseList(enterprise);
    }

    /**
     * 新增开票企业
     * 
     * @param enterprise 开票企业
     * @return 结果
     */
    @Override
    public int insertEnterprise(Enterprise enterprise)
    {
        enterprise.setCreateTime(DateUtils.getNowDate());
        return enterpriseMapper.insertEnterprise(enterprise);
    }

    /**
     * 修改开票企业
     * 
     * @param enterprise 开票企业
     * @return 结果
     */
    @Override
    public int updateEnterprise(Enterprise enterprise)
    {
        enterprise.setUpdateTime(DateUtils.getNowDate());
        return enterpriseMapper.updateEnterprise(enterprise);
    }

    /**
     * 批量删除开票企业
     * 
     * @param enterpriseIds 需要删除的开票企业主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseByEnterpriseIds(Long[] enterpriseIds)
    {
        return enterpriseMapper.deleteEnterpriseByEnterpriseIds(enterpriseIds);
    }

    /**
     * 删除开票企业信息
     * 
     * @param enterpriseId 开票企业主键
     * @return 结果
     */
    @Override
    public int deleteEnterpriseByEnterpriseId(Long enterpriseId)
    {
        return enterpriseMapper.deleteEnterpriseByEnterpriseId(enterpriseId);
    }
}
