package com.ruoyi.system.pc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Enterprise;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.mapper.PcInvoiceStatsMapper;
import com.ruoyi.system.pc.support.PcDict;
import com.ruoyi.system.pc.vo.PcInvoiceOverviewVO;
import com.ruoyi.system.pc.vo.PcInvoiceTicketVO;
import com.ruoyi.system.service.IEnterpriseService;

/**
 * PC 开票概览与真实发票查询（D03）
 *
 * 统计口径（AC36）：只统计真实发票（t_invoice），按实际开票日期归集，蓝红分别计数；
 * invoice_no 唯一保证同一张票只计一次；申请与确认单不计张数；平台不重算税额。
 */
@Service
public class PcInvoiceQueryService {

    @Autowired
    private PcInvoiceStatsMapper statsMapper;

    @Autowired
    private IEnterpriseService enterpriseService;

    /**
     * 企业开票概览：蓝红数量、票面价税合计、趋势、票种分布
     */
    public PcInvoiceOverviewVO overview(PcRequests.EnterpriseOverviewQuery query) {
        PcInvoiceOverviewVO vo = new PcInvoiceOverviewVO();
        if (query.getEnterpriseId() != null) {
            Enterprise enterprise = enterpriseService.selectEnterpriseByEnterpriseId(query.getEnterpriseId());
            if (enterprise == null) {
                throw new ServiceException("企业不存在");
            }
            vo.setEnterpriseId(query.getEnterpriseId());
            vo.setEnterpriseName(enterprise.getEnterpriseName());
        }

        long blue = zeroIfNull(statsMapper.countByKind(query, "1"));
        long red = zeroIfNull(statsMapper.countByKind(query, "2"));
        vo.setBlueCount(blue);
        vo.setRedCount(red);
        vo.setTotalCount(blue + red);
        vo.setTotalPriceTaxDisplay(PcDict.amount(statsMapper.sumPriceTax(query)));

        List<PcInvoiceOverviewVO.TrendPoint> trend = statsMapper.selectTrend(query);
        vo.setTrend(trend == null ? new ArrayList<>() : trend);

        List<PcInvoiceOverviewVO.TypeDist> dist = statsMapper.selectTypeDist(query);
        if (dist != null) {
            for (PcInvoiceOverviewVO.TypeDist item : dist) {
                item.setInvoiceTypeText(PcDict.text(PcDict.INVOICE_TYPE, item.getInvoiceType()));
            }
            vo.setTypeDist(dist);
        }

        vo.setStartDate(query.getStartDate());
        vo.setEndDate(query.getEndDate());
        return vo;
    }

    /**
     * 一票一行分页列表（分页由 Controller 控制）
     */
    public List<PcInvoiceTicketVO> tickets(PcRequests.InvoiceTicketQuery query) {
        return decorate(statsMapper.selectTicketList(query));
    }

    /**
     * 指定实际发票（D05-S4：打开指定票，不默认打开另一张）
     */
    public PcInvoiceTicketVO ticket(Long invoiceId) {
        if (invoiceId == null) {
            throw new ServiceException("参数缺失：invoiceId");
        }
        PcInvoiceTicketVO vo = statsMapper.selectTicketById(invoiceId);
        if (vo == null) {
            throw new ServiceException("发票不存在");
        }
        return decorateOne(vo);
    }

    /**
     * 某开票申请关联的真实发票（一票一行）
     */
    public List<PcInvoiceTicketVO> ticketsByApply(Long applyId) {
        return decorate(statsMapper.selectTicketsByApplyId(applyId));
    }

    /**
     * 某红字申请关联的真实红票
     */
    public List<PcInvoiceTicketVO> ticketsByRedApply(Long redApplyId) {
        return decorate(statsMapper.selectTicketsByRedApplyId(redApplyId));
    }

    /**
     * 展示口径：状态/票种文本、金额「待返回」、是否可下载
     */
    public PcInvoiceTicketVO decorateOne(PcInvoiceTicketVO vo) {
        if (vo == null) {
            return null;
        }
        vo.setInvoiceKindText(PcDict.text(PcDict.INVOICE_KIND, vo.getInvoiceKind()));
        vo.setInvoiceTypeText(PcDict.text(PcDict.INVOICE_TYPE, vo.getInvoiceType()));
        vo.setAmountDisplay(PcDict.amount(parse(vo.getTotalPriceTax())));
        vo.setDownloadable(hasText(vo.getPdfUrl()) || hasText(vo.getOfdUrl()) || hasText(vo.getXmlUrl()));
        return vo;
    }

    private List<PcInvoiceTicketVO> decorate(List<PcInvoiceTicketVO> list) {
        List<PcInvoiceTicketVO> result = new ArrayList<>();
        if (list != null) {
            for (PcInvoiceTicketVO vo : list) {
                result.add(decorateOne(vo));
            }
        }
        return result;
    }

    private long zeroIfNull(Long value) {
        return value == null ? 0L : value;
    }

    private BigDecimal parse(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isEmpty();
    }
}
