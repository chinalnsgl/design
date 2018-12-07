package com.zw.design.modules.system.sectiontype.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.sectiontype.entity.SectionType;
import com.zw.design.modules.system.sectiontype.query.SectionTypeQuery;
import com.zw.design.modules.system.sectiontype.service.SectionTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class SectionTypeController {

    private String prefix = "system";

    @Autowired
    private SectionTypeService sectionTypeService;

    /**
     * 到达部门类型列表页面
     */
    @GetMapping("/sectypes")
    @RequiresPermissions({"sectype:list"})
    public String users() {
        return prefix + "/sectionType/list";
    }
    /**
     * 部门类型列表数据
     */
    @ResponseBody
    @PostMapping("/sectype/list")
    @RequiresPermissions({"sectype:list"})
    public BaseResponse sectionTypeList(SectionTypeQuery query) {
        BaseDataTableModel<SectionType> dto = sectionTypeService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除部门类型
     */
    @ResponseBody
    @PostMapping("/sectype/status")
    @RequiresPermissions(value = {"sectype:del"})
    public BaseResponse updateSectionTypeStatus(@RequestParam("id")Integer id) {
        SectionType sectionType = sectionTypeService.updateSectionTypeStatus(id,0);
        return BaseResponse.toResponse(sectionType);
    }


    /**
     * 添加页面
     */
    @GetMapping("/sectype/create")
    @RequiresPermissions({"sectype:create"})
    public String sectionTypeCreate() {
        return  prefix + "/sectionType/create";
    }
    /**
     * 部门类型唯一验证
     */
    @ResponseBody
    @PostMapping("/sectype/checkSecTypeNameUnique")
    @RequiresPermissions({"sectype:create"})
    public BaseValidResponse checkSectionTypeNameUnique(@RequestParam(value = "name") String name, @RequestParam(value = "id", required = false) Integer id ) {
        SectionType sectionType = sectionTypeService.findByName(name);
        if (id == null) {
            return BaseValidResponse.toResponse(sectionType);
        }
        if (sectionType == null || sectionType.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存部门类型
     */
    @ResponseBody
    @PostMapping("/sectype/save")
    @RequiresPermissions({"sectype:create"})
    public BaseResponse sectionTypeSave(SectionType sectionType) {
        SectionType secType = sectionTypeService.saveSectionType(sectionType);
        return BaseResponse.toResponse(secType);
    }


    /**
     * 修改部门类型
     */
    @ResponseBody
    @PostMapping("/sectype/update")
    @RequiresPermissions({"sectype:edit"})
    public BaseResponse sectionTypeUpdate(SectionType sectionType) {
        SectionType secType = sectionTypeService.updateSectionType(sectionType);
        return BaseResponse.toResponse(secType);
    }
}
