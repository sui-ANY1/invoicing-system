package com.ruoyi.system.mini.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 新建接口统一返回：data 里放新记录主键
 */
@Schema(description = "新建结果")
public class IdResultVO {

    @Schema(description = "新建记录的主键ID", example = "1")
    private Long id;

    public IdResultVO() {
    }

    public IdResultVO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
