package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegCompanyMapper;
import com.ruoyi.system.domain.RegCompany;
import com.ruoyi.system.service.IRegCompanyService;

/**
 * 公司注册资料Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RegCompanyServiceImpl implements IRegCompanyService 
{
    @Autowired
    private RegCompanyMapper regCompanyMapper;

    /**
     * 查询公司注册资料
     * 
     * @param companyId 公司注册资料主键
     * @return 公司注册资料
     */
    @Override
    public RegCompany selectRegCompanyByCompanyId(Long companyId)
    {
        return regCompanyMapper.selectRegCompanyByCompanyId(companyId);
    }

    /**
     * 查询公司注册资料列表
     * 
     * @param regCompany 公司注册资料
     * @return 公司注册资料
     */
    @Override
    public List<RegCompany> selectRegCompanyList(RegCompany regCompany)
    {
        return regCompanyMapper.selectRegCompanyList(regCompany);
    }

    /**
     * 新增公司注册资料
     * 
     * @param regCompany 公司注册资料
     * @return 结果
     */
    @Override
    public int insertRegCompany(RegCompany regCompany)
    {
        regCompany.setCreateTime(DateUtils.getNowDate());
        return regCompanyMapper.insertRegCompany(regCompany);
    }

    /**
     * 修改公司注册资料
     * 
     * @param regCompany 公司注册资料
     * @return 结果
     */
    @Override
    public int updateRegCompany(RegCompany regCompany)
    {
        regCompany.setUpdateTime(DateUtils.getNowDate());
        return regCompanyMapper.updateRegCompany(regCompany);
    }

    /**
     * 批量删除公司注册资料
     * 
     * @param companyIds 需要删除的公司注册资料主键
     * @return 结果
     */
    @Override
    public int deleteRegCompanyByCompanyIds(Long[] companyIds)
    {
        return regCompanyMapper.deleteRegCompanyByCompanyIds(companyIds);
    }

    /**
     * 删除公司注册资料信息
     * 
     * @param companyId 公司注册资料主键
     * @return 结果
     */
    @Override
    public int deleteRegCompanyByCompanyId(Long companyId)
    {
        return regCompanyMapper.deleteRegCompanyByCompanyId(companyId);
    }
}
