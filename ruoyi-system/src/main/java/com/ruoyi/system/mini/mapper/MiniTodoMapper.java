package com.ruoyi.system.mini.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.mini.dto.vo.MiniTodoVO;

/**
 * 小程序首页待办 Mapper
 *
 * 仅提供首页「需要我处理」所需的待办查询。
 */
public interface MiniTodoMapper {

    /**
     * 查询当前会员待完成的税务验证待办
     *
     * @param memberId 会员ID
     * @return 待办集合
     */
    public List<MiniTodoVO> selectPendingTaxVerifyTodos(@Param("memberId") Long memberId);
}
