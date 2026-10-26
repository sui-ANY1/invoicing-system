package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.InvoiceMapper;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.service.IInvoiceService;

/**
 * 发票信息Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class InvoiceServiceImpl implements IInvoiceService 
{
    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     * 查询发票信息
     * 
     * @param invoiceId 发票信息主键
     * @return 发票信息
     */
    @Override
    public Invoice selectInvoiceByInvoiceId(Long invoiceId)
    {
        return invoiceMapper.selectInvoiceByInvoiceId(invoiceId);
    }

    /**
     * 查询发票信息列表
     * 
     * @param invoice 发票信息
     * @return 发票信息
     */
    @Override
    public List<Invoice> selectInvoiceList(Invoice invoice)
    {
        return invoiceMapper.selectInvoiceList(invoice);
    }

    /**
     * 新增发票信息
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    @Override
    public int insertInvoice(Invoice invoice)
    {
        invoice.setCreateTime(DateUtils.getNowDate());
        return invoiceMapper.insertInvoice(invoice);
    }

    /**
     * 修改发票信息
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    @Override
    public int updateInvoice(Invoice invoice)
    {
        invoice.setUpdateTime(DateUtils.getNowDate());
        return invoiceMapper.updateInvoice(invoice);
    }

    /**
     * 批量删除发票信息
     * 
     * @param invoiceIds 需要删除的发票信息主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceByInvoiceIds(Long[] invoiceIds)
    {
        return invoiceMapper.deleteInvoiceByInvoiceIds(invoiceIds);
    }

    /**
     * 删除发票信息信息
     * 
     * @param invoiceId 发票信息主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceByInvoiceId(Long invoiceId)
    {
        return invoiceMapper.deleteInvoiceByInvoiceId(invoiceId);
    }
}
