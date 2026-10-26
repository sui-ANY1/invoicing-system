package com.ruoyi.system.mini.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 发票文件预览/下载请求
 */
@Schema(description = "发票文件预览/下载请求")
public class InvoiceFileRequest {

    @Schema(description = "发票记录ID（t_invoice.invoice_id，见开票详情 invoices[].invoiceId）",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "12")
    private Long invoiceId;

    @Schema(description = "文件格式：pdf（默认）或 ofd", example = "pdf")
    private String format;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
