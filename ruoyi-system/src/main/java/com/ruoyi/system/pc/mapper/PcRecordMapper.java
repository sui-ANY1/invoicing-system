package com.ruoyi.system.pc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.vo.PcInvoiceTicketVO;
import com.ruoyi.system.pc.vo.PcRecordItemVO;

/**
 * PC 开票/红字记录与统计查询（D03/D04）
 */
public interface PcRecordMapper {

    /**
     * D04 开票与红字统一记录分页（合并分页）
     */
    List<PcRecordItemVO> selectRecordList(@Param("q") PcRequests.RecordQuery query);
}
