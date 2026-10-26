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
import com.ruoyi.system.domain.RedItem;
import com.ruoyi.system.service.IRedItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 红字明细Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/red/item")
public class RedItemController extends BaseController
{
    @Autowired
    private IRedItemService redItemService;

    /**
     * 查询红字明细列表
     */
    @PreAuthorize("@ss.hasPermi('red:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedItem redItem)
    {
        startPage();
        List<RedItem> list = redItemService.selectRedItemList(redItem);
        return getDataTable(list);
    }

    /**
     * 导出红字明细列表
     */
    @PreAuthorize("@ss.hasPermi('red:item:export')")
    @Log(title = "红字明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedItem redItem)
    {
        List<RedItem> list = redItemService.selectRedItemList(redItem);
        ExcelUtil<RedItem> util = new ExcelUtil<RedItem>(RedItem.class);
        util.exportExcel(response, list, "红字明细数据");
    }

    /**
     * 获取红字明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('red:item:query')")
    @GetMapping(value = "/{redItemId}")
    public AjaxResult getInfo(@PathVariable("redItemId") Long redItemId)
    {
        return success(redItemService.selectRedItemByRedItemId(redItemId));
    }

    /**
     * 新增红字明细
     */
    @PreAuthorize("@ss.hasPermi('red:item:add')")
    @Log(title = "红字明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RedItem redItem)
    {
        return toAjax(redItemService.insertRedItem(redItem));
    }

    /**
     * 修改红字明细
     */
    @PreAuthorize("@ss.hasPermi('red:item:edit')")
    @Log(title = "红字明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RedItem redItem)
    {
        return toAjax(redItemService.updateRedItem(redItem));
    }

    /**
     * 删除红字明细
     */
    @PreAuthorize("@ss.hasPermi('red:item:remove')")
    @Log(title = "红字明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{redItemIds}")
    public AjaxResult remove(@PathVariable Long[] redItemIds)
    {
        return toAjax(redItemService.deleteRedItemByRedItemIds(redItemIds));
    }
}
