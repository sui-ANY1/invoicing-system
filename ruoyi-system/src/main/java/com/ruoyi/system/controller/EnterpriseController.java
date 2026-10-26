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
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.service.IEnterpriseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开票企业Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/cert/enterprise")
public class EnterpriseController extends BaseController
{
    @Autowired
    private IEnterpriseService enterpriseService;

    /**
     * 查询开票企业列表
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:list')")
    @GetMapping("/list")
    public TableDataInfo list(Enterprise enterprise)
    {
        startPage();
        List<Enterprise> list = enterpriseService.selectEnterpriseList(enterprise);
        return getDataTable(list);
    }

    /**
     * 导出开票企业列表
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:export')")
    @Log(title = "开票企业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Enterprise enterprise)
    {
        List<Enterprise> list = enterpriseService.selectEnterpriseList(enterprise);
        ExcelUtil<Enterprise> util = new ExcelUtil<Enterprise>(Enterprise.class);
        util.exportExcel(response, list, "开票企业数据");
    }

    /**
     * 获取开票企业详细信息
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:query')")
    @GetMapping(value = "/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable("enterpriseId") Long enterpriseId)
    {
        return success(enterpriseService.selectEnterpriseByEnterpriseId(enterpriseId));
    }

    /**
     * 新增开票企业
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:add')")
    @Log(title = "开票企业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Enterprise enterprise)
    {
        return toAjax(enterpriseService.insertEnterprise(enterprise));
    }

    /**
     * 修改开票企业
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:edit')")
    @Log(title = "开票企业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Enterprise enterprise)
    {
        return toAjax(enterpriseService.updateEnterprise(enterprise));
    }

    /**
     * 删除开票企业
     */
    @PreAuthorize("@ss.hasPermi('cert:enterprise:remove')")
    @Log(title = "开票企业", businessType = BusinessType.DELETE)
	@DeleteMapping("/{enterpriseIds}")
    public AjaxResult remove(@PathVariable Long[] enterpriseIds)
    {
        return toAjax(enterpriseService.deleteEnterpriseByEnterpriseIds(enterpriseIds));
    }
}
