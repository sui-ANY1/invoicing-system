package com.ruoyi.system.pc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.pc.service.PcFileService;
import com.ruoyi.system.pc.support.PcFileStreams;

/**
 * PC 受控文件输出（D03 行内下载真实发票、D05 补件附件）
 *
 * 统一约定：GET + query 参数（无路径参数），文件以流返回（未交付只提示，不重新开票）。
 */
@Tag(name = "文件下载", description = "D03 / D05 受控文件输出，返回二进制流而非 JSON（GET + query 参数）")
@RestController
@RequestMapping("/pc")
public class PcFileController extends BaseController {

    @Autowired
    private PcFileService pcFileService;

    /**
     * 真实发票文件下载（query format：pdf 优先 / ofd / xml；按附件下载，不浏览器内联打开）
     */
    @Operation(summary = "下载发票文件",
            description = "GET /pc/invoices/file：query 参数 invoiceId + 可选 format（pdf 优先 / ofd / xml）；"
                    + "以附件形式返回（Content-Disposition: attachment）")
    @ApiResponse(responseCode = "200", description = "文件流",
            content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary")))
    @PreAuthorize("@ss.hasPermi('pc:file:download')")
    @GetMapping("/invoices/file")
    public void invoiceFile(@RequestParam("invoiceId") Long invoiceId,
            @RequestParam(value = "format", required = false) String format,
            HttpServletResponse response) {
        PcFileStreams.write(pcFileService.invoiceFile(invoiceId, format), response, true);
    }

    /**
     * 业务附件受控预览（按 attachment_id，不暴露平台路径）
     */
    @Operation(summary = "下载业务附件",
            description = "GET /pc/attachments/file：query 参数 attachmentId，D05 业务附件输出，输出文件流（inline 预览），"
                    + "不暴露平台路径")
    @ApiResponse(responseCode = "200", description = "文件流",
            content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary")))
    @PreAuthorize("@ss.hasPermi('pc:file:download')")
    @GetMapping("/attachments/file")
    public void attachmentFile(@RequestParam("attachmentId") Long attachmentId, HttpServletResponse response) {
        PcFileStreams.write(pcFileService.attachmentFile(attachmentId, null), response);
    }
}
