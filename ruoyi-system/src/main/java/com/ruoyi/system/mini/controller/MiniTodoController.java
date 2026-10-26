package com.ruoyi.system.mini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.vo.MiniTodoVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.mapper.MiniTodoMapper;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.support.MiniPageUtils;

/**
 * 小程序首页待办
 *
 * 首页「需要我处理」列表：当前阶段只实现原型 P01 的「税务验证待完成」一类。
 */
@Tag(name = "首页待办", description = "首页「需要我处理」的待办列表")
@RestController
@RequestMapping("/api/mini/todos")
public class MiniTodoController {

    @Autowired
    private MiniTodoMapper todoMapper;

    @Operation(summary = "查询首页待办",
            description = "返回当前会员「需要我处理」的待办；当前仅实现「税务验证待完成」一类；分页参数 pageNum/pageSize（默认 1/10，上限 200）；返回 {total,rows}")
    @GetMapping
    public R<PageResult<MiniTodoVO>> list(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<MiniTodoVO> todos = todoMapper.selectPendingTaxVerifyTodos(memberId);
        if (todos != null) {
            for (MiniTodoVO todo : todos) {
                todo.setType("taxVerify");
                todo.setTypeText("税务验证待完成");
                todo.setActionText("去验证");
            }
        }
        return R.ok(MiniPageUtils.of(todos, pageNum, pageSize));
    }
}
