package com.zw.design.modules.baseinfosetting.sectiontype.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.baseinfosetting.sectiontype.entity.SectionType;
import com.zw.design.modules.baseinfosetting.sectiontype.query.SectionTypeQuery;
import com.zw.design.modules.baseinfosetting.sectiontype.service.SectionTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/baseinfo/sectype")
public class SectionTypeController {

    private String prefix = "baseinfo/sectionType";

    @Autowired
    private SectionTypeService sectionTypeService;

    /**
     * 到达部门类型列表页面
     */
    @GetMapping("/page")
    @RequiresRoles({"admin"})
    public String users() {
        return prefix + "/list";
    }
    /**
     * 部门类型列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresRoles({"admin"})
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
    @PostMapping("/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateSectionTypeStatus(@RequestParam("id")Integer id) {
        SectionType sectionType = sectionTypeService.updateSectionTypeStatus(id,0);
        return BaseResponse.toResponse(sectionType);
    }


    /**
     * 添加页面
     */
    @GetMapping("/create")
    @RequiresRoles({"admin"})
    public String sectionTypeCreate() {
        return  prefix + "/create";
    }
    /**
     * 部门类型唯一验证
     */
    @ResponseBody
    @PostMapping("/checkSecTypeNameUnique")
    @RequiresRoles({"admin"})
    public BaseValidResponse checkSectionTypeNameUnique(@RequestParam(value = "name") String name, @RequestParam(value = "id", required = false) Integer id ) {
        SectionType sectionType = sectionTypeService.findByNameAndStatus(name, 1);
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
    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public BaseResponse sectionTypeSave(SectionType sectionType) {
        SectionType secType = sectionTypeService.saveSectionType(sectionType);
        return BaseResponse.toResponse(secType);
    }


    /**
     * 修改部门类型
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresRoles({"admin"})
    public BaseResponse sectionTypeUpdate(SectionType sectionType) {
        SectionType secType = sectionTypeService.updateSectionType(sectionType);
        return BaseResponse.toResponse(secType);
    }
}
