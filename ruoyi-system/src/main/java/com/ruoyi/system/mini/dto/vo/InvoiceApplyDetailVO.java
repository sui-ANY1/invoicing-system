package com.ruoyi.system.mini.dto.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "开票申请详情")
public class InvoiceApplyDetailVO {

    @Schema(description = "主单信息")
    private InvoiceApplyVO apply;

    @Schema(description = "明细列表")
    private List<InvoiceItemVO> items;

    @Schema(description = "真实发票列表（一票一行，蓝红区分，无则为空数组）")
    private List<InvoiceTicketVO> invoices;

    public List<InvoiceTicketVO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceTicketVO> invoices) {
        this.invoices = invoices;
    }

    public InvoiceApplyVO getApply() {
        return apply;
    }

    public void setApply(InvoiceApplyVO apply) {
        this.apply = apply;
    }

    public List<InvoiceItemVO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemVO> items) {
        this.items = items;
    }
}
