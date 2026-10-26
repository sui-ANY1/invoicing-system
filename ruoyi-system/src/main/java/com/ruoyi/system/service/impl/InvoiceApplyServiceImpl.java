package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.InvoiceApplyMapper;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.service.IInvoiceApplyService;

/**
 * 开票申请Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class InvoiceApplyServiceImpl implements IInvoiceApplyService 
{
    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;

    /**
     * 查询开票申请
     * 
     * @param applyId 开票申请主键
     * @return 开票申请
     */
    @Override
    public InvoiceApply selectInvoiceApplyByApplyId(Long applyId)
    {
        return invoiceApplyMapper.selectInvoiceApplyByApplyId(applyId);
    }

    /**
     * 查询开票申请列表
     * 
     * @param invoiceApply 开票申请
     * @return 开票申请
     */
    @Override
    public List<InvoiceApply> selectInvoiceApplyList(InvoiceApply invoiceApply)
    {
        return invoiceApplyMapper.selectInvoiceApplyList(invoiceApply);
    }

    /**
     * 新增开票申请
     * 
     * @param invoiceApply 开票申请
     * @return 结果
     */
    @Override
    public int insertInvoiceApply(InvoiceApply invoiceApply)
    {
        invoiceApply.setCreateTime(DateUtils.getNowDate());
        return invoiceApplyMapper.insertInvoiceApply(invoiceApply);
    }

    /**
     * 修改开票申请
     * 
     * @param invoiceApply 开票申请
     * @return 结果
     */
    @Override
    public int updateInvoiceApply(InvoiceApply invoiceApply)
    {
        invoiceApply.setUpdateTime(DateUtils.getNowDate());
        return invoiceApplyMapper.updateInvoiceApply(invoiceApply);
    }

    /**
     * 批量删除开票申请
     * 
     * @param applyIds 需要删除的开票申请主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceApplyByApplyIds(Long[] applyIds)
    {
        return invoiceApplyMapper.deleteInvoiceApplyByApplyIds(applyIds);
    }

    /**
     * 删除开票申请信息
     * 
     * @param applyId 开票申请主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceApplyByApplyId(Long applyId)
    {
        return invoiceApplyMapper.deleteInvoiceApplyByApplyId(applyId);
    }
}
