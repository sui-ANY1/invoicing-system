package com.ruoyi.system.pc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.vo.PcInvoiceOverviewVO;
import com.ruoyi.system.pc.vo.PcInvoiceTicketVO;

/**
 * PC 发票统计与真实票据查询（D03）
 *
 * 口径：只统计 t_invoice（真实发票）；按企业 + 实际开票日期过滤；
 * invoice_no 唯一约束保证同一发票只计一次；金额取票面回传值。
 */
public interface PcInvoiceStatsMapper {

    /**
     * 蓝红开票数量趋势（按月/按日）
     */
    List<PcInvoiceOverviewVO.TrendPoint> selectTrend(@Param("q") PcRequests.EnterpriseOverviewQuery query);

    /**
     * 票种分布（普票/专票，仅蓝字）
     */
    List<PcInvoiceOverviewVO.TypeDist> selectTypeDist(@Param("q") PcRequests.EnterpriseOverviewQuery query);

    /**
     * 按票种统计张数（kind：1蓝字 2红字）
     */
    Long countByKind(@Param("q") PcRequests.EnterpriseOverviewQuery query, @Param("kind") String kind);

    /**
     * 区间内票面价税合计（票面回传值累加，可能为 null）
     */
    java.math.BigDecimal sumPriceTax(@Param("q") PcRequests.EnterpriseOverviewQuery query);

    /**
     * 一张真实发票一行（分页）
     */
    List<PcInvoiceTicketVO> selectTicketList(@Param("q") PcRequests.InvoiceTicketQuery query);

    /**
     * 单个真实发票（受控下载/详情定位用）
     */
    PcInvoiceTicketVO selectTicketById(@Param("invoiceId") Long invoiceId);

    /**
     * 某开票申请下的真实发票
     */
    List<PcInvoiceTicketVO> selectTicketsByApplyId(@Param("applyId") Long applyId);

    /**
     * 某红字申请下的真实红票
     */
    List<PcInvoiceTicketVO> selectTicketsByRedApplyId(@Param("redApplyId") Long redApplyId);
}
