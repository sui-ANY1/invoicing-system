package com.ruoyi.system.mini.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.domain.InvoiceApply;
import com.ruoyi.system.service.IInvoiceApplyService;
import com.ruoyi.system.service.IInvoiceService;

/**
 * 小程序发票文件受控访问
 *
 * @author sui-ANY1
 */
@Service
public class MiniInvoiceFileService {
    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IInvoiceApplyService applyService;

    /**
     * 校验归属、格式与文件是否可获取，返回发票记录
     */
    public Invoice getOwnedInvoice(Long memberId, Long invoiceId, String format) {
        Invoice invoice = invoiceService.selectInvoiceByInvoiceId(invoiceId);
        if (invoice == null) {
            throw new RuntimeException("发票不存在");
        }
        if ("2".equals(invoice.getInvoiceKind())) {
            throw new RuntimeException("红字发票文件将在后续开放");
        }
        InvoiceApply apply = applyService.selectInvoiceApplyByApplyId(invoice.getApplyId());
        if (apply == null || !memberId.equals(apply.getMemberId())) {
            throw new RuntimeException("无权操作该发票");
        }
        String fmt = StringUtils.isEmpty(format) ? "pdf" : format.toLowerCase();
        if (!"pdf".equals(fmt) && !"ofd".equals(fmt)) {
            throw new RuntimeException("不支持的文件格式：" + format);
        }
        String url = "ofd".equals(fmt) ? invoice.getOfdUrl() : invoice.getPdfUrl();
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("发票文件尚未交付，请稍后再试");
        }
        return invoice;
    }

    /**
     * 把 /profile 相对路径解析为磁盘文件并校验存在
     */
    public File toDiskFile(String relativeUrl) {
        String relative = relativeUrl;
        if (relative.startsWith(Constants.RESOURCE_PREFIX)) {
            relative = relative.substring(Constants.RESOURCE_PREFIX.length());
        }
        File file = new File(RuoYiConfig.getProfile(), relative);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("发票文件丢失或尚未交付");
        }
        return file;
    }
}
