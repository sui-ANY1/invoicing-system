package com.ruoyi.system.mini.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ruoyi.system.mini.dto.R;
import com.ruoyi.system.mini.dto.vo.MessageVO;
import com.ruoyi.system.mini.dto.vo.PageResult;
import com.ruoyi.system.mini.security.MiniSecurityUtils;
import com.ruoyi.system.mini.service.MiniMessageService;
import com.ruoyi.system.mini.support.MiniPageUtils;

/**
 * 小程序站内消息
 *
 * 统一约定：RESTful 资源路径；进入消息中心（GET 列表）即把本次可见的未读消息标记为已读，
 * 返回的是标记前的状态，红点用于本次进入时的提示。
 */
@Tag(name = "消息通知", description = "站内消息列表（进入即标记已读）")
@RestController
@RequestMapping("/api/mini/messages")
public class MiniMessageController {

    @Autowired
    private MiniMessageService messageService;

    @Operation(summary = "查询消息列表",
            description = "查询当前会员的站内消息；分页参数 pageNum/pageSize（默认 1/10，上限 200）；"
                    + "可选 read 筛选（true已读/false未读）；进入列表时自动把本次返回的未读消息标记为已读（按原型：进入即标记），"
                    + "返回体中的 read 仍为标记前的状态；返回 {total,rows}")
    @GetMapping
    public R<PageResult<MessageVO>> list(
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "read", required = false) Boolean read) {
        Long memberId = MiniSecurityUtils.getMemberId();
        List<MessageVO> list = messageService.list(memberId);
        PageResult<MessageVO> page = MiniPageUtils.of(list, pageNum, pageSize,
                vo -> read == null || read.equals("1".equals(vo.getIsRead())));
        // 进入消息中心即标记已读：只标记本次返回的未读消息
        List<Long> unreadIds = new ArrayList<>();
        for (MessageVO vo : page.getRows()) {
            if (!"1".equals(vo.getIsRead()) && vo.getId() != null) {
                unreadIds.add(vo.getId());
            }
        }
        if (!unreadIds.isEmpty()) {
            messageService.read(memberId, unreadIds);
        }
        return R.ok(page);
    }
}
