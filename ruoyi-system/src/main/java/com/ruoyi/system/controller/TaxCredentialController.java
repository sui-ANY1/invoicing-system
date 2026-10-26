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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.TaxCredential;
import com.ruoyi.system.mini.security.MiniCryptoUtils;
import com.ruoyi.system.service.ITaxCredentialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 税务凭据Controller
 *
 * <p>账密安全口径：税务密码一律由服务端 AES-256-GCM 加密后落库，
 * 接口列表/详情/导出<b>永不返回密文</b>；提交方只提交明文，密文提交直接拒绝。
 * 
 * @author dsx
 * @date 2026-10-13
 */
@RestController
@RequestMapping("/cert/credential")
public class TaxCredentialController extends BaseController
{
    @Autowired
    private ITaxCredentialService taxCredentialService;

    @Autowired
    private MiniCryptoUtils cryptoUtils;

    /**
     * 查询税务凭据列表
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaxCredential taxCredential)
    {
        startPage();
        List<TaxCredential> list = taxCredentialService.selectTaxCredentialList(taxCredential);
        list.forEach(this::maskPassword);
        return getDataTable(list);
    }

    /**
     * 导出税务凭据列表
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:export')")
    @Log(title = "税务凭据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaxCredential taxCredential)
    {
        List<TaxCredential> list = taxCredentialService.selectTaxCredentialList(taxCredential);
        list.forEach(this::maskPassword);
        ExcelUtil<TaxCredential> util = new ExcelUtil<TaxCredential>(TaxCredential.class);
        util.exportExcel(response, list, "税务凭据数据");
    }

    /**
     * 获取税务凭据详细信息
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:query')")
    @GetMapping(value = "/{credentialId}")
    public AjaxResult getInfo(@PathVariable("credentialId") Long credentialId)
    {
        TaxCredential credential = taxCredentialService.selectTaxCredentialByCredentialId(credentialId);
        maskPassword(credential);
        return success(credential);
    }

    /**
     * 新增税务凭据
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:add')")
    @Log(title = "税务凭据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaxCredential taxCredential)
    {
        encryptPassword(taxCredential, true);
        return toAjax(taxCredentialService.insertTaxCredential(taxCredential));
    }

    /**
     * 修改税务凭据
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:edit')")
    @Log(title = "税务凭据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaxCredential taxCredential)
    {
        encryptPassword(taxCredential, false);
        return toAjax(taxCredentialService.updateTaxCredential(taxCredential));
    }

    /**
     * 删除税务凭据
     */
    @PreAuthorize("@ss.hasPermi('cert:credential:remove')")
    @Log(title = "税务凭据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{credentialIds}")
    public AjaxResult remove(@PathVariable Long[] credentialIds)
    {
        return toAjax(taxCredentialService.deleteTaxCredentialByCredentialIds(credentialIds));
    }

    /**
     * 密文永不出接口：列表/详情/导出一律清空税务密码字段
     */
    private void maskPassword(TaxCredential credential)
    {
        if (credential != null)
        {
            credential.setTaxPassword(null);
        }
    }

    /**
     * 入库前处理税务密码：明文 → AES-256-GCM 密文并记录密钥版本；
     * 修改时不传密码表示保持原值；不接受已加密密文重复提交。
     *
     * @param credential 凭据
     * @param isNew      是否新增
     */
    private void encryptPassword(TaxCredential credential, boolean isNew)
    {
        if (credential == null)
        {
            return;
        }
        String password = credential.getTaxPassword();
        if (StringUtils.isEmpty(password))
        {
            // 修改时未提交密码 → 保持原密文不变（mapper 对 null 不更新）
            credential.setTaxPassword(null);
            return;
        }
        if (password.startsWith(MiniCryptoUtils.PREFIX_V2))
        {
            throw new ServiceException("税务密码请提交明文，由服务端加密存储");
        }
        credential.setTaxPassword(cryptoUtils.encrypt(password));
        credential.setSecretKeyVersion(cryptoUtils.keyVersion());
        if (isNew)
        {
            if (credential.getCredentialVersion() == null)
            {
                credential.setCredentialVersion(1L);
            }
            return;
        }
        // 修改密码 → 凭据版本递增，便于审计与排查
        TaxCredential current = taxCredentialService.selectTaxCredentialByCredentialId(credential.getCredentialId());
        long currentVersion = (current == null || current.getCredentialVersion() == null)
                ? 1L : current.getCredentialVersion();
        credential.setCredentialVersion(currentVersion + 1);
    }
}
