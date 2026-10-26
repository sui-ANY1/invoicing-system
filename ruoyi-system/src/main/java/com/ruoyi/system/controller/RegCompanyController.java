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
import com.ruoyi.system.domain.RegCompany;
import com.ruoyi.system.service.IRegCompanyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司注册资料Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/reg/company")
public class RegCompanyController extends BaseController
{
    @Autowired
    private IRegCompanyService regCompanyService;

    /**
     * 查询公司注册资料列表
     */
    @PreAuthorize("@ss.hasPermi('reg:company:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegCompany regCompany)
    {
        startPage();
        List<RegCompany> list = regCompanyService.selectRegCompanyList(regCompany);
        return getDataTable(list);
    }

    /**
     * 导出公司注册资料列表
     */
    @PreAuthorize("@ss.hasPermi('reg:company:export')")
    @Log(title = "公司注册资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegCompany regCompany)
    {
        List<RegCompany> list = regCompanyService.selectRegCompanyList(regCompany);
        ExcelUtil<RegCompany> util = new ExcelUtil<RegCompany>(RegCompany.class);
        util.exportExcel(response, list, "公司注册资料数据");
    }

    /**
     * 获取公司注册资料详细信息
     */
    @PreAuthorize("@ss.hasPermi('reg:company:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        return success(regCompanyService.selectRegCompanyByCompanyId(companyId));
    }

    /**
     * 新增公司注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:company:add')")
    @Log(title = "公司注册资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegCompany regCompany)
    {
        return toAjax(regCompanyService.insertRegCompany(regCompany));
    }

    /**
     * 修改公司注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:company:edit')")
    @Log(title = "公司注册资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegCompany regCompany)
    {
        return toAjax(regCompanyService.updateRegCompany(regCompany));
    }

    /**
     * 删除公司注册资料
     */
    @PreAuthorize("@ss.hasPermi('reg:company:remove')")
    @Log(title = "公司注册资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds)
    {
        return toAjax(regCompanyService.deleteRegCompanyByCompanyIds(companyIds));
    }
}
