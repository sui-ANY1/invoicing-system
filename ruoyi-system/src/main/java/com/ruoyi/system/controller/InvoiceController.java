package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.service.IInvoiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发票信息Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/billing/invoice")
public class InvoiceController extends BaseController
{
    @Autowired
    private IInvoiceService invoiceService;

    /**
     * 查询发票信息列表
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(Invoice invoice)
    {
        startPage();
        List<Invoice> list = invoiceService.selectInvoiceList(invoice);
        return getDataTable(list);
    }

    /**
     * 导出发票信息列表
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:export')")
    @Log(title = "发票信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Invoice invoice)
    {
        List<Invoice> list = invoiceService.selectInvoiceList(invoice);
        ExcelUtil<Invoice> util = new ExcelUtil<Invoice>(Invoice.class);
        util.exportExcel(response, list, "发票信息数据");
    }

    /**
     * 获取发票信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:query')")
    @GetMapping(value = "/{invoiceId}")
    public AjaxResult getInfo(@PathVariable("invoiceId") Long invoiceId)
    {
        return success(invoiceService.selectInvoiceByInvoiceId(invoiceId));
    }

    /**
     * 新增发票信息
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:add')")
    @Log(title = "发票信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Invoice invoice)
    {
        return toAjax(invoiceService.insertInvoice(invoice));
    }

    /**
     * 修改发票信息
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:edit')")
    @Log(title = "发票信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Invoice invoice)
    {
        return toAjax(invoiceService.updateInvoice(invoice));
    }

    /**
     * 删除发票信息
     */
    @PreAuthorize("@ss.hasPermi('billing:invoice:remove')")
    @Log(title = "发票信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{invoiceIds}")
    public AjaxResult remove(@PathVariable Long[] invoiceIds)
    {
        return toAjax(invoiceService.deleteInvoiceByInvoiceIds(invoiceIds));
    }
}
