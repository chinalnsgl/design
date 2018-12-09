package com.zw.design.modules.system.section.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.section.entity.Section;
import com.zw.design.modules.system.section.query.SectionQuery;
import com.zw.design.modules.system.section.service.SectionService;
import com.zw.design.modules.system.sectiontype.service.SectionTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class SectionController {

    private String prefix = "system";

    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionTypeService sectionTypeService;

    /**
     * 到达部门列表页面
     */
    @GetMapping("/secs")
    @RequiresPermissions({"sec:list"})
    public String users(Model model) {
        model.addAttribute("sectionType", sectionTypeService.findByStatus(1));
        return prefix + "/section/list";
    }
    /**
     * 部门列表数据
     */
    @ResponseBody
    @PostMapping("/sec/list")
    @RequiresPermissions({"sec:list"})
    public BaseResponse SectionList(SectionQuery query) {
        BaseDataTableModel<Section> dto = sectionService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除部门
     */
    @ResponseBody
    @PostMapping("/sec/status")
    @RequiresPermissions(value = {"sec:del"})
    public BaseResponse updateSectionStatus(@RequestParam("id")Integer id) {
        Section section = sectionService.updateSectionStatus(id,0);
        return BaseResponse.toResponse(section);
    }


    /**
     * 添加页面
     */
    @GetMapping("/sec/create")
    @RequiresPermissions({"sec:create"})
    public String SectionCreate(Model model) {
        model.addAttribute("sectionType", sectionTypeService.findByStatus(1));
        return  prefix + "/section/create";
    }

    /**
     * 部门唯一验证
     */
    @ResponseBody
    @PostMapping("/sec/checkSectionNameUnique")
    @RequiresPermissions({"sec:create"})
    public BaseValidResponse checkSectionNameUnique(@RequestParam("name") String name, @RequestParam(value = "id", required = false) Integer id) {
        Section section = sectionService.findByNameAndStatus(name, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(section);
        }
        if (section == null || section.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存部门
     */
    @ResponseBody
    @PostMapping("/sec/save")
    @RequiresPermissions({"sec:create"})
    public BaseResponse sectionSave(Section section) {
        Section sec = sectionService.saveSection(section);
        return BaseResponse.toResponse(sec);
    }


    /**
     * 修改部门
     */
    @ResponseBody
    @PostMapping("/sec/update")
    @RequiresPermissions({"sec:edit"})
    public BaseResponse sectionUpdate(Section Section) {
        Section sec = sectionService.updateSection(Section);
        return BaseResponse.toResponse(sec);
    }
}
