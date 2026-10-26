package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Invoice;

/**
 * 发票信息Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IInvoiceService 
{
    /**
     * 查询发票信息
     * 
     * @param invoiceId 发票信息主键
     * @return 发票信息
     */
    public Invoice selectInvoiceByInvoiceId(Long invoiceId);

    /**
     * 查询发票信息列表
     * 
     * @param invoice 发票信息
     * @return 发票信息集合
     */
    public List<Invoice> selectInvoiceList(Invoice invoice);

    /**
     * 新增发票信息
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    public int insertInvoice(Invoice invoice);

    /**
     * 修改发票信息
     * 
     * @param invoice 发票信息
     * @return 结果
     */
    public int updateInvoice(Invoice invoice);

    /**
     * 批量删除发票信息
     * 
     * @param invoiceIds 需要删除的发票信息主键集合
     * @return 结果
     */
    public int deleteInvoiceByInvoiceIds(Long[] invoiceIds);

    /**
     * 删除发票信息信息
     * 
     * @param invoiceId 发票信息主键
     * @return 结果
     */
    public int deleteInvoiceByInvoiceId(Long invoiceId);
}
