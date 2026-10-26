package com.ruoyi.system.mini.dto.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "红字申请详情")
public class RedApplyDetailVO {

    @Schema(description = "红字申请主信息")
    private RedApplyVO apply;

    @Schema(description = "红字明细列表（全部红冲时为空）")
    private List<RedItemVO> items;

    public RedApplyVO getApply() {
        return apply;
    }

    public void setApply(RedApplyVO apply) {
        this.apply = apply;
    }

    public List<RedItemVO> getItems() {
        return items;
    }

    public void setItems(List<RedItemVO> items) {
        this.items = items;
    }
}
