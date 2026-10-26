package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ManualResultMapper;
import com.ruoyi.system.domain.ManualResult;
import com.ruoyi.system.service.IManualResultService;

/**
 * 人工办理结果Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class ManualResultServiceImpl implements IManualResultService 
{
    @Autowired
    private ManualResultMapper manualResultMapper;

    /**
     * 查询人工办理结果
     * 
     * @param resultId 人工办理结果主键
     * @return 人工办理结果
     */
    @Override
    public ManualResult selectManualResultByResultId(Long resultId)
    {
        return manualResultMapper.selectManualResultByResultId(resultId);
    }

    /**
     * 查询人工办理结果列表
     * 
     * @param manualResult 人工办理结果
     * @return 人工办理结果
     */
    @Override
    public List<ManualResult> selectManualResultList(ManualResult manualResult)
    {
        return manualResultMapper.selectManualResultList(manualResult);
    }

    /**
     * 新增人工办理结果
     * 
     * @param manualResult 人工办理结果
     * @return 结果
     */
    @Override
    public int insertManualResult(ManualResult manualResult)
    {
        manualResult.setCreateTime(DateUtils.getNowDate());
        return manualResultMapper.insertManualResult(manualResult);
    }

    /**
     * 修改人工办理结果
     * 
     * @param manualResult 人工办理结果
     * @return 结果
     */
    @Override
    public int updateManualResult(ManualResult manualResult)
    {
        return manualResultMapper.updateManualResult(manualResult);
    }

    /**
     * 批量删除人工办理结果
     * 
     * @param resultIds 需要删除的人工办理结果主键
     * @return 结果
     */
    @Override
    public int deleteManualResultByResultIds(Long[] resultIds)
    {
        return manualResultMapper.deleteManualResultByResultIds(resultIds);
    }

    /**
     * 删除人工办理结果信息
     * 
     * @param resultId 人工办理结果主键
     * @return 结果
     */
    @Override
    public int deleteManualResultByResultId(Long resultId)
    {
        return manualResultMapper.deleteManualResultByResultId(resultId);
    }
}
