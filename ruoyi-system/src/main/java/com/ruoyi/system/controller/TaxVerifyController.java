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
import com.ruoyi.system.domain.TaxVerify;
import com.ruoyi.system.service.ITaxVerifyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 税务验证Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/billing/verify")
public class TaxVerifyController extends BaseController
{
    @Autowired
    private ITaxVerifyService taxVerifyService;

    /**
     * 查询税务验证列表
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaxVerify taxVerify)
    {
        startPage();
        List<TaxVerify> list = taxVerifyService.selectTaxVerifyList(taxVerify);
        return getDataTable(list);
    }

    /**
     * 导出税务验证列表
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:export')")
    @Log(title = "税务验证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaxVerify taxVerify)
    {
        List<TaxVerify> list = taxVerifyService.selectTaxVerifyList(taxVerify);
        ExcelUtil<TaxVerify> util = new ExcelUtil<TaxVerify>(TaxVerify.class);
        util.exportExcel(response, list, "税务验证数据");
    }

    /**
     * 获取税务验证详细信息
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:query')")
    @GetMapping(value = "/{verifyId}")
    public AjaxResult getInfo(@PathVariable("verifyId") Long verifyId)
    {
        return success(taxVerifyService.selectTaxVerifyByVerifyId(verifyId));
    }

    /**
     * 新增税务验证
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:add')")
    @Log(title = "税务验证", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaxVerify taxVerify)
    {
        return toAjax(taxVerifyService.insertTaxVerify(taxVerify));
    }

    /**
     * 修改税务验证
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:edit')")
    @Log(title = "税务验证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaxVerify taxVerify)
    {
        return toAjax(taxVerifyService.updateTaxVerify(taxVerify));
    }

    /**
     * 删除税务验证
     */
    @PreAuthorize("@ss.hasPermi('billing:verify:remove')")
    @Log(title = "税务验证", businessType = BusinessType.DELETE)
	@DeleteMapping("/{verifyIds}")
    public AjaxResult remove(@PathVariable Long[] verifyIds)
    {
        return toAjax(taxVerifyService.deleteTaxVerifyByVerifyIds(verifyIds));
    }
}
