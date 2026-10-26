package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "标记已读结果")
public class ReadResultVO {

    @Schema(description = "本次标记为已读的消息条数", example = "3")
    private Integer updated;

    public ReadResultVO() {
    }

    public ReadResultVO(Integer updated) {
        this.updated = updated;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }
}
