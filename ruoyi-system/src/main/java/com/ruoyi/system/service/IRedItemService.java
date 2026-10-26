package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RedItem;

/**
 * 红字明细Service接口
 * 
 * @author dsx
 * @date 2026-10-13
 */
public interface IRedItemService 
{
    /**
     * 查询红字明细
     * 
     * @param redItemId 红字明细主键
     * @return 红字明细
     */
    public RedItem selectRedItemByRedItemId(Long redItemId);

    /**
     * 查询红字明细列表
     * 
     * @param redItem 红字明细
     * @return 红字明细集合
     */
    public List<RedItem> selectRedItemList(RedItem redItem);

    /**
     * 新增红字明细
     * 
     * @param redItem 红字明细
     * @return 结果
     */
    public int insertRedItem(RedItem redItem);

    /**
     * 修改红字明细
     * 
     * @param redItem 红字明细
     * @return 结果
     */
    public int updateRedItem(RedItem redItem);

    /**
     * 批量删除红字明细
     * 
     * @param redItemIds 需要删除的红字明细主键集合
     * @return 结果
     */
    public int deleteRedItemByRedItemIds(Long[] redItemIds);

    /**
     * 删除红字明细信息
     * 
     * @param redItemId 红字明细主键
     * @return 结果
     */
    public int deleteRedItemByRedItemId(Long redItemId);
}
