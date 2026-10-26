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
import com.ruoyi.system.domain.RegSole;
import com.ruoyi.system.service.IRegSoleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个体户注册资料Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/reg/sole")
public class RegSoleController extends BaseController
{
    @Autowired
    private IRegSoleService regSoleService;

    /**
     * 查询个体户注册资料列表
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegSole regSole)
    {
        startPage();
        List<RegSole> list = regSoleService.selectRegSoleList(regSole);
        return getDataTable(list);
    }

    /**
     * 导出个体户注册资料列表
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:export')")
    @Log(title = "个体户注册资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegSole regSole)
    {
        List<RegSole> list = regSoleService.selectRegSoleList(regSole);
        ExcelUtil<RegSole> util = new ExcelUtil<RegSole>(RegSole.class);
        util.exportExcel(response, list, "个体户注册资料数据");
    }

    /**
     * 获取个体户注册资料详细信息
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:query')")
    @GetMapping(value = "/{soleId}")
    public AjaxResult getInfo(@PathVariable("soleId") Long soleId)
    {
        return success(regSoleService.selectRegSoleBySoleId(soleId));
    }

    /**
     * 新增个体户注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:add')")
    @Log(title = "个体户注册资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegSole regSole)
    {
        return toAjax(regSoleService.insertRegSole(regSole));
    }

    /**
     * 修改个体户注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:edit')")
    @Log(title = "个体户注册资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegSole regSole)
    {
        return toAjax(regSoleService.updateRegSole(regSole));
    }

    /**
     * 删除个体户注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:sole:remove')")
    @Log(title = "个体户注册资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{soleIds}")
    public AjaxResult remove(@PathVariable Long[] soleIds)
    {
        return toAjax(regSoleService.deleteRegSoleBySoleIds(soleIds));
    }
}
