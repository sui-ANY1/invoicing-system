package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TaxVerifyMapper;
import com.ruoyi.system.domain.TaxVerify;
import com.ruoyi.system.service.ITaxVerifyService;

/**
 * 税务验证Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class TaxVerifyServiceImpl implements ITaxVerifyService 
{
    @Autowired
    private TaxVerifyMapper taxVerifyMapper;

    /**
     * 查询税务验证
     * 
     * @param verifyId 税务验证主键
     * @return 税务验证
     */
    @Override
    public TaxVerify selectTaxVerifyByVerifyId(Long verifyId)
    {
        return taxVerifyMapper.selectTaxVerifyByVerifyId(verifyId);
    }

    /**
     * 查询税务验证列表
     * 
     * @param taxVerify 税务验证
     * @return 税务验证
     */
    @Override
    public List<TaxVerify> selectTaxVerifyList(TaxVerify taxVerify)
    {
        return taxVerifyMapper.selectTaxVerifyList(taxVerify);
    }

    /**
     * 新增税务验证
     * 
     * @param taxVerify 税务验证
     * @return 结果
     */
    @Override
    public int insertTaxVerify(TaxVerify taxVerify)
    {
        taxVerify.setCreateTime(DateUtils.getNowDate());
        return taxVerifyMapper.insertTaxVerify(taxVerify);
    }

    /**
     * 修改税务验证
     * 
     * @param taxVerify 税务验证
     * @return 结果
     */
    @Override
    public int updateTaxVerify(TaxVerify taxVerify)
    {
        taxVerify.setUpdateTime(DateUtils.getNowDate());
        return taxVerifyMapper.updateTaxVerify(taxVerify);
    }

    /**
     * 批量删除税务验证
     * 
     * @param verifyIds 需要删除的税务验证主键
     * @return 结果
     */
    @Override
    public int deleteTaxVerifyByVerifyIds(Long[] verifyIds)
    {
        return taxVerifyMapper.deleteTaxVerifyByVerifyIds(verifyIds);
    }

    /**
     * 删除税务验证信息
     * 
     * @param verifyId 税务验证主键
     * @return 结果
     */
    @Override
    public int deleteTaxVerifyByVerifyId(Long verifyId)
    {
        return taxVerifyMapper.deleteTaxVerifyByVerifyId(verifyId);
    }
}
