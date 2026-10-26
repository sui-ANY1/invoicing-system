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
import com.ruoyi.system.domain.EnterpriseContact;
import com.ruoyi.system.service.IEnterpriseContactService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业经办人Controller
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/cert/contact")
public class EnterpriseContactController extends BaseController
{
    @Autowired
    private IEnterpriseContactService enterpriseContactService;

    /**
     * 查询企业经办人列表
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:list')")
    @GetMapping("/list")
    public TableDataInfo list(EnterpriseContact enterpriseContact)
    {
        startPage();
        List<EnterpriseContact> list = enterpriseContactService.selectEnterpriseContactList(enterpriseContact);
        return getDataTable(list);
    }

    /**
     * 导出企业经办人列表
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:export')")
    @Log(title = "企业经办人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EnterpriseContact enterpriseContact)
    {
        List<EnterpriseContact> list = enterpriseContactService.selectEnterpriseContactList(enterpriseContact);
        ExcelUtil<EnterpriseContact> util = new ExcelUtil<EnterpriseContact>(EnterpriseContact.class);
        util.exportExcel(response, list, "企业经办人数据");
    }

    /**
     * 获取企业经办人详细信息
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:query')")
    @GetMapping(value = "/{contactId}")
    public AjaxResult getInfo(@PathVariable("contactId") Long contactId)
    {
        return success(enterpriseContactService.selectEnterpriseContactByContactId(contactId));
    }

    /**
     * 新增企业经办人
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:add')")
    @Log(title = "企业经办人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EnterpriseContact enterpriseContact)
    {
        return toAjax(enterpriseContactService.insertEnterpriseContact(enterpriseContact));
    }

    /**
     * 修改企业经办人
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:edit')")
    @Log(title = "企业经办人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EnterpriseContact enterpriseContact)
    {
        return toAjax(enterpriseContactService.updateEnterpriseContact(enterpriseContact));
    }

    /**
     * 删除企业经办人
     */
    @PreAuthorize("@ss.hasPermi('cert:contact:remove')")
    @Log(title = "企业经办人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contactIds}")
    public AjaxResult remove(@PathVariable Long[] contactIds)
    {
        return toAjax(enterpriseContactService.deleteEnterpriseContactByContactIds(contactIds));
    }
}
