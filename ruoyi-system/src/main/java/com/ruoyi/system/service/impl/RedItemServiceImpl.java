package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RedItemMapper;
import com.ruoyi.system.domain.RedItem;
import com.ruoyi.system.service.IRedItemService;

/**
 * 红字明细Service业务层处理
 * 
 * @author dsx
 * @date 2026-10-13
 */
@Service
public class RedItemServiceImpl implements IRedItemService 
{
    @Autowired
    private RedItemMapper redItemMapper;

    /**
     * 查询红字明细
     * 
     * @param redItemId 红字明细主键
     * @return 红字明细
     */
    @Override
    public RedItem selectRedItemByRedItemId(Long redItemId)
    {
        return redItemMapper.selectRedItemByRedItemId(redItemId);
    }

    /**
     * 查询红字明细列表
     * 
     * @param redItem 红字明细
     * @return 红字明细
     */
    @Override
    public List<RedItem> selectRedItemList(RedItem redItem)
    {
        return redItemMapper.selectRedItemList(redItem);
    }

    /**
     * 新增红字明细
     * 
     * @param redItem 红字明细
     * @return 结果
     */
    @Override
    public int insertRedItem(RedItem redItem)
    {
        redItem.setCreateTime(DateUtils.getNowDate());
        return redItemMapper.insertRedItem(redItem);
    }

    /**
     * 修改红字明细
     * 
     * @param redItem 红字明细
     * @return 结果
     */
    @Override
    public int updateRedItem(RedItem redItem)
    {
        redItem.setUpdateTime(DateUtils.getNowDate());
        return redItemMapper.updateRedItem(redItem);
    }

    /**
     * 批量删除红字明细
     * 
     * @param redItemIds 需要删除的红字明细主键
     * @return 结果
     */
    @Override
    public int deleteRedItemByRedItemIds(Long[] redItemIds)
    {
        return redItemMapper.deleteRedItemByRedItemIds(redItemIds);
    }

    /**
     * 删除红字明细信息
     * 
     * @param redItemId 红字明细主键
     * @return 结果
     */
    @Override
    public int deleteRedItemByRedItemId(Long redItemId)
    {
        return redItemMapper.deleteRedItemByRedItemId(redItemId);
    }
}
