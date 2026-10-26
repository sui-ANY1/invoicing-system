package com.ruoyi.system.pc.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * D03 企业开票概览：蓝红开票数量趋势 + 票种分布 + 去重后的真实票据计数
 *
 * 口径（AC36）：只统计真实发票（t_invoice），按企业 + 实际开票日期区间过滤，
 * 蓝红分别计数，同一发票标识只计一次（t_invoice.invoice_no 唯一约束保证），
 * 开票申请与红字确认单不计入张数，金额取票面回传值、平台不计算。
 */
@Schema(description = "企业开票概览（D03）")
public class PcInvoiceOverviewVO {

    @Schema(description = "企业 id")
    private Long enterpriseId;

    @Schema(description = "企业名称")
    private String enterpriseName;

    @Schema(description = "蓝字发票张数")
    private Long blueCount;

    @Schema(description = "红字发票张数")
    private Long redCount;

    @Schema(description = "真实票据总张数（蓝 + 红）")
    private Long totalCount;

    @Schema(description = "区间内票面价税合计（票面回传值累加，未回传则为 null）")
    private String totalPriceTaxDisplay;

    @Schema(description = "统计区间起（yyyy-MM-dd，为空表示不限）")
    private String startDate;

    @Schema(description = "统计区间止（yyyy-MM-dd）")
    private String endDate;

    @Schema(description = "数量趋势（蓝红分序列）")
    private List<TrendPoint> trend = new ArrayList<>();

    @Schema(description = "票种分布（普票/专票）")
    private List<TypeDist> typeDist = new ArrayList<>();

    @Schema(description = "口径说明")
    private String ruleNote = "仅统计真实发票；按实际开票日期归集；同一发票号码只计一次；申请与确认单不计张数；金额为票面回传值，平台不重算";

    @Schema(description = "数量趋势点")
    public static class TrendPoint {
        @Schema(description = "周期：按月 yyyy-MM 或按日 yyyy-MM-dd")
        private String period;

        @Schema(description = "蓝字张数")
        private Long blueCount;

        @Schema(description = "红字张数")
        private Long redCount;

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public Long getBlueCount() {
            return blueCount;
        }

        public void setBlueCount(Long blueCount) {
            this.blueCount = blueCount;
        }

        public Long getRedCount() {
            return redCount;
        }

        public void setRedCount(Long redCount) {
            this.redCount = redCount;
        }
    }

    @Schema(description = "票种分布项")
    public static class TypeDist {
        @Schema(description = "开票类型：1普票 2专票")
        private String invoiceType;

        @Schema(description = "开票类型名称")
        private String invoiceTypeText;

        @Schema(description = "张数")
        private Long count;

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getInvoiceTypeText() {
            return invoiceTypeText;
        }

        public void setInvoiceTypeText(String invoiceTypeText) {
            this.invoiceTypeText = invoiceTypeText;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Long getBlueCount() {
        return blueCount;
    }

    public void setBlueCount(Long blueCount) {
        this.blueCount = blueCount;
    }

    public Long getRedCount() {
        return redCount;
    }

    public void setRedCount(Long redCount) {
        this.redCount = redCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPriceTaxDisplay() {
        return totalPriceTaxDisplay;
    }

    public void setTotalPriceTaxDisplay(String totalPriceTaxDisplay) {
        this.totalPriceTaxDisplay = totalPriceTaxDisplay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<TrendPoint> getTrend() {
        return trend;
    }

    public void setTrend(List<TrendPoint> trend) {
        this.trend = trend;
    }

    public List<TypeDist> getTypeDist() {
        return typeDist;
    }

    public void setTypeDist(List<TypeDist> typeDist) {
        this.typeDist = typeDist;
    }

    public String getRuleNote() {
        return ruleNote;
    }

    public void setRuleNote(String ruleNote) {
        this.ruleNote = ruleNote;
    }
}
