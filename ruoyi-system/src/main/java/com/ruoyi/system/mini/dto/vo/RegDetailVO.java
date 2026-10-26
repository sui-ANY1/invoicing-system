package com.ruoyi.system.mini.dto.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "注册收件详情")
public class RegDetailVO {

    @Schema(description = "注册收件主信息")
    private RegApplyVO apply;

    @Schema(description = "有限公司资料（主体类型=1 时返回）")
    private RegCompanyVO company;

    @Schema(description = "股东列表（有限公司）")
    private List<RegShareholderVO> shareholders;

    @Schema(description = "个体户资料（主体类型=2 时返回）")
    private RegSoleVO sole;

    @Schema(description = "已提交材料（附件）清单，按附件ID升序；附件可用 attachmentId 走受控预览")
    private List<MaterialVO> materials;

    public RegApplyVO getApply() {
        return apply;
    }

    public void setApply(RegApplyVO apply) {
        this.apply = apply;
    }

    public RegCompanyVO getCompany() {
        return company;
    }

    public void setCompany(RegCompanyVO company) {
        this.company = company;
    }

    public List<RegShareholderVO> getShareholders() {
        return shareholders;
    }

    public void setShareholders(List<RegShareholderVO> shareholders) {
        this.shareholders = shareholders;
    }

    public RegSoleVO getSole() {
        return sole;
    }

    public void setSole(RegSoleVO sole) {
        this.sole = sole;
    }

    public List<MaterialVO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialVO> materials) {
        this.materials = materials;
    }
}
