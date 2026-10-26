package com.ruoyi.system.mini.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "标记消息已读请求")
public class MessageReadRequest {

    @Schema(description = "要标记的消息ID列表；不传或传空数组表示全部已读", example = "[1, 2, 3]")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
