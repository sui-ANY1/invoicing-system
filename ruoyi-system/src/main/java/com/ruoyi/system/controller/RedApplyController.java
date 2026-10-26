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
import com.ruoyi.system.domain.RedApply;
import com.ruoyi.system.service.IRedApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 红字申请Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/red/apply")
public class RedApplyController extends BaseController
{
    @Autowired
    private IRedApplyService redApplyService;

    /**
     * 查询红字申请列表
     */
    @PreAuthorize("@ss.hasPermi('red:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedApply redApply)
    {
        startPage();
        List<RedApply> list = redApplyService.selectRedApplyList(redApply);
        return getDataTable(list);
    }

    /**
     * 导出红字申请列表
     */
    @PreAuthorize("@ss.hasPermi('red:apply:export')")
    @Log(title = "红字申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedApply redApply)
    {
        List<RedApply> list = redApplyService.selectRedApplyList(redApply);
        ExcelUtil<RedApply> util = new ExcelUtil<RedApply>(RedApply.class);
        util.exportExcel(response, list, "红字申请数据");
    }

    /**
     * 获取红字申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('red:apply:query')")
    @GetMapping(value = "/{redApplyId}")
    public AjaxResult getInfo(@PathVariable("redApplyId") Long redApplyId)
    {
        return success(redApplyService.selectRedApplyByRedApplyId(redApplyId));
    }

    /**
     * 新增红字申请
     */
    @PreAuthorize("@ss.hasPermi('red:apply:add')")
    @Log(title = "红字申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RedApply redApply)
    {
        return toAjax(redApplyService.insertRedApply(redApply));
    }

    /**
     * 修改红字申请
     */
    @PreAuthorize("@ss.hasPermi('red:apply:edit')")
    @Log(title = "红字申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RedApply redApply)
    {
        return toAjax(redApplyService.updateRedApply(redApply));
    }

    /**
     * 删除红字申请
     */
    @PreAuthorize("@ss.hasPermi('red:apply:remove')")
    @Log(title = "红字申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{redApplyIds}")
    public AjaxResult remove(@PathVariable Long[] redApplyIds)
    {
        return toAjax(redApplyService.deleteRedApplyByRedApplyIds(redApplyIds));
    }
}
