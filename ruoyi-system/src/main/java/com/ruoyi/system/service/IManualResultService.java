package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ManualResult;

/**
 * 人工办理结果Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IManualResultService 
{
    /**
     * 查询人工办理结果
     * 
     * @param resultId 人工办理结果主键
     * @return 人工办理结果
     */
    public ManualResult selectManualResultByResultId(Long resultId);

    /**
     * 查询人工办理结果列表
     * 
     * @param manualResult 人工办理结果
     * @return 人工办理结果集合
     */
    public List<ManualResult> selectManualResultList(ManualResult manualResult);

    /**
     * 新增人工办理结果
     * 
     * @param manualResult 人工办理结果
     * @return 结果
     */
    public int insertManualResult(ManualResult manualResult);

    /**
     * 修改人工办理结果
     * 
     * @param manualResult 人工办理结果
     * @return 结果
     */
    public int updateManualResult(ManualResult manualResult);

    /**
     * 批量删除人工办理结果
     * 
     * @param resultIds 需要删除的人工办理结果主键集合
     * @return 结果
     */
    public int deleteManualResultByResultIds(Long[] resultIds);

    /**
     * 删除人工办理结果信息
     * 
     * @param resultId 人工办理结果主键
     * @return 结果
     */
    public int deleteManualResultByResultId(Long resultId);
}
