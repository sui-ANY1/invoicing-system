package com.ruoyi.system.mini.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.vo.MiniInvoiceDetailVO;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniInvoiceDetailService;
import com.ruoyi.system.mini.service.MiniInvoiceFileService;

/**
 * 小程序发票文件受控访问
 *
 * 统一约定：GET + query 参数（invoiceId、format），响应为二进制文件流。
 */
@Tag(name = "发票文件", description = "发票详情查询与 PDF/OFD 文件预览下载（GET，query 传 invoiceId）")
@RestController
@RequestMapping("/api/mini/invoices")
public class MiniInvoiceFileController {

    @Autowired
    private MiniInvoiceFileService fileService;

    @Autowired
    private MiniInvoiceDetailService detailService;

    @Operation(summary = "查询发票详情",
            description = "query 参数 invoiceId；返回票面信息、文件可用性、剩余可处理金额与红字资格（P19 发票详情）")
    @GetMapping("/detail")
    public R<MiniInvoiceDetailVO> detail(@RequestParam("invoiceId") Long invoiceId) {
        Long memberId = MiniSecurityUtils.getMemberId();
        return R.ok(detailService.detail(memberId, invoiceId));
    }

    @Operation(summary = "预览/下载发票文件",
            description = "直接返回文件流（不是 JSON，Apifox 里选“文件”响应）；query 参数 invoiceId，format 传 pdf（默认）或 ofd")
    @GetMapping("/file")
    public void file(@RequestParam("invoiceId") Long invoiceId,
                     @RequestParam(value = "format", required = false) String format,
                     HttpServletResponse response) throws Exception {
        Long memberId = MiniSecurityUtils.getMemberId();
        String fmt = StringUtils.isEmpty(format) ? "pdf" : format.toLowerCase();

        // 全部业务校验在写响应前完成，失败时由全局异常处理返回 JSON
        Invoice invoice = fileService.getOwnedInvoice(memberId, invoiceId, fmt);
        String url = "ofd".equals(fmt) ? invoice.getOfdUrl() : invoice.getPdfUrl();
        File file = fileService.toDiskFile(url);

        String contentType = "ofd".equals(fmt) ? "application/ofd" : "application/pdf";
        String suffix = "ofd".equals(fmt) ? ".ofd" : ".pdf";
        String downloadName = (StringUtils.isEmpty(invoice.getInvoiceNo())
                ? String.valueOf(invoiceId) : invoice.getInvoiceNo()) + suffix;
        String encoded = URLEncoder.encode(downloadName, StandardCharsets.UTF_8).replace("+", "%20");

        response.setContentType(contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition",
                "inline; filename=\"" + encoded + "\"; filename*=UTF-8''" + encoded);
        Files.copy(file.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }
}
