package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * RPA 任务对象 t_rpa_task
 * 
 * @author dsx
 * @date 2026-10-13
 */
public class RpaTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** RPA 任务 id */
    @Schema(description = "RPA任务ID", example = "1")
    private Long taskId;

    /** 平台任务号（唯一） */
    @Schema(description = "平台任务号（唯一）", example = "RPA2026101600001")
    @Excel(name = "平台任务号", readConverterExp = "唯=一")
    private String taskNo;

    /** 业务类型（1蓝字 2红字） */
    @Schema(description = "业务类型（1蓝字 2红字）", example = "1")
    @Excel(name = "业务类型", readConverterExp = "1=蓝字,2=红字")
    private String bizType;

    /** 关联业务 id（开票申请/红字申请 id） */
    @Schema(description = "关联业务ID（开票申请/红字申请ID）", example = "1")
    @Excel(name = "关联业务 id", readConverterExp = "开=票申请/红字申请,i=d")
    private Long bizId;

    /** RPA 系统侧任务号 */
    @Schema(description = "RPA系统侧任务号", example = "TASK-20261016-0001")
    @Excel(name = "RPA 系统侧任务号")
    private String rpaTaskNo;

    /** 任务状态（0待下发 1待用户验证 2执行中 3结果待核对 4已开票待文件 5已完成 6明确失败 7已停止） */
    @Schema(description = "任务状态（0待下发 1待用户验证 2执行中 3结果待核对 4已开票待文件 5已完成 6明确失败 7已停止）", example = "0")
    @Excel(name = "任务状态", readConverterExp = "0=待下发,1=待用户验证,2=执行中,3=结果待核对,4=已开票待文件,5=已完成,6=明确失败,7=已停止")
    private String taskStatus;

    /** 下次查询时间（轮询用） */
    @Schema(description = "下次查询时间（轮询用）", example = "2026-10-16 10:00:00")
    @Excel(name = "下次查询时间", readConverterExp = "轮=询用")
    private Date nextQueryTime;

    /** 失败原因 */
    @Schema(description = "失败原因", example = "税务平台登录超时")
    @Excel(name = "失败原因")
    private String failReason;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setTaskNo(String taskNo) 
    {
        this.taskNo = taskNo;
    }

    public String getTaskNo() 
    {
        return taskNo;
    }

    public void setBizType(String bizType) 
    {
        this.bizType = bizType;
    }

    public String getBizType() 
    {
        return bizType;
    }

    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }

    public void setRpaTaskNo(String rpaTaskNo) 
    {
        this.rpaTaskNo = rpaTaskNo;
    }

    public String getRpaTaskNo() 
    {
        return rpaTaskNo;
    }

    public void setTaskStatus(String taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() 
    {
        return taskStatus;
    }

    public void setNextQueryTime(Date nextQueryTime) 
    {
        this.nextQueryTime = nextQueryTime;
    }

    public Date getNextQueryTime() 
    {
        return nextQueryTime;
    }

    public void setFailReason(String failReason) 
    {
        this.failReason = failReason;
    }

    public String getFailReason() 
    {
        return failReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskNo", getTaskNo())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("rpaTaskNo", getRpaTaskNo())
            .append("taskStatus", getTaskStatus())
            .append("nextQueryTime", getNextQueryTime())
            .append("failReason", getFailReason())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
