package com.ruoyi.system.pc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.pc.dto.PcRequests;
import com.ruoyi.system.pc.vo.PcTodoItemVO;

/**
 * PC 工作台（D01）待办聚合查询
 */
public interface PcWorkbenchMapper {

    /**
     * 待办分页列表（多来源合并）
     */
    List<PcTodoItemVO> selectTodoList(@Param("q") PcRequests.TodoQuery query);

    /**
     * 待审核：开票申请待审核 + 红字申请待审核
     */
    long countPendingAudit();

    /**
     * 需补资料：最新审核结论为「要求补充」且未终结
     */
    long countNeedMaterial();

    /**
     * 待用户操作：RPA 待用户验证 + 税务验证待提交/待验证
     */
    long countWaitingUser();

    /**
     * 结果未知：RPA 结果待核对 + 申请处于结果核对中
     */
    long countUnknownResult();
}
