package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.RegCompany;

/**
 * 公司注册资料Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface RegCompanyMapper 
{
    /**
     * 查询公司注册资料
     * 
     * @param companyId 公司注册资料主键
     * @return 公司注册资料
     */
    public RegCompany selectRegCompanyByCompanyId(Long companyId);

    /**
     * 查询公司注册资料列表
     * 
     * @param regCompany 公司注册资料
     * @return 公司注册资料集合
     */
    public List<RegCompany> selectRegCompanyList(RegCompany regCompany);

    /**
     * 新增公司注册资料
     * 
     * @param regCompany 公司注册资料
     * @return 结果
     */
    public int insertRegCompany(RegCompany regCompany);

    /**
     * 修改公司注册资料
     * 
     * @param regCompany 公司注册资料
     * @return 结果
     */
    public int updateRegCompany(RegCompany regCompany);

    /**
     * 删除公司注册资料
     * 
     * @param companyId 公司注册资料主键
     * @return 结果
     */
    public int deleteRegCompanyByCompanyId(Long companyId);

    /**
     * 批量删除公司注册资料
     * 
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegCompanyByCompanyIds(Long[] companyIds);
}
