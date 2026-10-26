package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TaxVerify;

/**
 * 税务验证Mapper接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface TaxVerifyMapper 
{
    /**
     * 查询税务验证
     * 
     * @param verifyId 税务验证主键
     * @return 税务验证
     */
    public TaxVerify selectTaxVerifyByVerifyId(Long verifyId);

    /**
     * 查询税务验证列表
     * 
     * @param taxVerify 税务验证
     * @return 税务验证集合
     */
    public List<TaxVerify> selectTaxVerifyList(TaxVerify taxVerify);

    /**
     * 新增税务验证
     * 
     * @param taxVerify 税务验证
     * @return 结果
     */
    public int insertTaxVerify(TaxVerify taxVerify);

    /**
     * 修改税务验证
     * 
     * @param taxVerify 税务验证
     * @return 结果
     */
    public int updateTaxVerify(TaxVerify taxVerify);

    /**
     * 删除税务验证
     * 
     * @param verifyId 税务验证主键
     * @return 结果
     */
    public int deleteTaxVerifyByVerifyId(Long verifyId);

    /**
     * 批量删除税务验证
     * 
     * @param verifyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaxVerifyByVerifyIds(Long[] verifyIds);
}
