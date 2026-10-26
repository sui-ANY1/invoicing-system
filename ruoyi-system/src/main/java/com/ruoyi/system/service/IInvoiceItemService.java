package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.InvoiceItem;

/**
 * 开票明细Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IInvoiceItemService 
{
    /**
     * 查询开票明细
     * 
     * @param itemId 开票明细主键
     * @return 开票明细
     */
    public InvoiceItem selectInvoiceItemByItemId(Long itemId);

    /**
     * 查询开票明细列表
     * 
     * @param invoiceItem 开票明细
     * @return 开票明细集合
     */
    public List<InvoiceItem> selectInvoiceItemList(InvoiceItem invoiceItem);

    /**
     * 新增开票明细
     * 
     * @param invoiceItem 开票明细
     * @return 结果
     */
    public int insertInvoiceItem(InvoiceItem invoiceItem);

    /**
     * 修改开票明细
     * 
     * @param invoiceItem 开票明细
     * @return 结果
     */
    public int updateInvoiceItem(InvoiceItem invoiceItem);

    /**
     * 批量删除开票明细
     * 
     * @param itemIds 需要删除的开票明细主键集合
     * @return 结果
     */
    public int deleteInvoiceItemByItemIds(Long[] itemIds);

    /**
     * 删除开票明细信息
     * 
     * @param itemId 开票明细主键
     * @return 结果
     */
    public int deleteInvoiceItemByItemId(Long itemId);
}
