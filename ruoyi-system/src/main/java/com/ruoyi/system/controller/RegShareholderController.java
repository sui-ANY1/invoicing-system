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
import com.ruoyi.system.domain.RegShareholder;
import com.ruoyi.system.service.IRegShareholderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 股东信息Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/reg/shareholder")
public class RegShareholderController extends BaseController
{
    @Autowired
    private IRegShareholderService regShareholderService;

    /**
     * 查询股东信息列表
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegShareholder regShareholder)
    {
        startPage();
        List<RegShareholder> list = regShareholderService.selectRegShareholderList(regShareholder);
        return getDataTable(list);
    }

    /**
     * 导出股东信息列表
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:export')")
    @Log(title = "股东信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegShareholder regShareholder)
    {
        List<RegShareholder> list = regShareholderService.selectRegShareholderList(regShareholder);
        ExcelUtil<RegShareholder> util = new ExcelUtil<RegShareholder>(RegShareholder.class);
        util.exportExcel(response, list, "股东信息数据");
    }

    /**
     * 获取股东信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:query')")
    @GetMapping(value = "/{shareholderId}")
    public AjaxResult getInfo(@PathVariable("shareholderId") Long shareholderId)
    {
        return success(regShareholderService.selectRegShareholderByShareholderId(shareholderId));
    }

    /**
     * 新增股东信息
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:add')")
    @Log(title = "股东信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegShareholder regShareholder)
    {
        return toAjax(regShareholderService.insertRegShareholder(regShareholder));
    }

    /**
     * 修改股东信息
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:edit')")
    @Log(title = "股东信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegShareholder regShareholder)
    {
        return toAjax(regShareholderService.updateRegShareholder(regShareholder));
    }

    /**
     * 删除股东信息
     */
    @PreAuthorize("@ss.hasPermi('reg:shareholder:remove')")
    @Log(title = "股东信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shareholderIds}")
    public AjaxResult remove(@PathVariable Long[] shareholderIds)
    {
        return toAjax(regShareholderService.deleteRegShareholderByShareholderIds(shareholderIds));
    }
}
