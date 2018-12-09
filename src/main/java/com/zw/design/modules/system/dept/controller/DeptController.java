package com.zw.design.modules.system.dept.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.dept.entity.Department;
import com.zw.design.modules.system.dept.service.DeptService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sys")
public class DeptController {

    private String prefix = "system";

    @Autowired
    private DeptService deptService;


    /**
     * 部门页面
     */
    @GetMapping("/depts")
    @RequiresPermissions({"dept:list"})
    public String depts() {
        return prefix + "/dept/list";
    }
    /**
     * 获取所有部门
     */
    @ResponseBody
    @GetMapping("/dept/all")
    @RequiresPermissions(value = {"dept:list","emp:list"}, logical = Logical.OR)
    public List<Department> findAllDept() {
        return deptService.findAllByStatus();
    }


    /**
     * 删除部门， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/dept/status")
    @RequiresPermissions({"dept:del"})
    public BaseResponse updateDeptStatus(@RequestParam("id")Integer id) {
        Department department = deptService.updateDeptStatus(id, 0);
        return BaseResponse.toResponse(department);
    }

    /**
     * 创建部门页面
     */
    @GetMapping("/dept/create")
    @RequiresPermissions({"dept:create"})
    public String deptCreate() {
        return  prefix + "/dept/create";
    }
    /**
     * 部门唯一验证
     */
    @ResponseBody
    @PostMapping("/dept/checkDeptNameUnique")
    @RequiresPermissions({"dept:create"})
    public BaseValidResponse checkDeptSectionNameUnique(@RequestParam("deptName") String deptName, @RequestParam(value = "id", required = false) Integer id) {
        Department section = deptService.findByNameAndStatus(deptName, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(section);
        }
        if (section == null || section.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 创建部门页面
     */
    @GetMapping("/dept/create/{id}")
    @RequiresPermissions({"dept:create"})
    public String deptCreate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/dept/create";
    }
    /**
     * 保存部门
     */
    @ResponseBody
    @PostMapping("/dept/save")
    @RequiresPermissions({"dept:create"})
    public BaseResponse deptSave(Department department) {
        Department dept = deptService.saveDept(department);
        return BaseResponse.toResponse(dept);
    }

    /**
     * 修改部门
     */
    @ResponseBody
    @PostMapping("/dept/update")
    @RequiresPermissions({"dept:edit"})
    public BaseResponse deptUpdate(Department department) {
        Department dept = deptService.updateDept(department);
        return BaseResponse.toResponse(dept);
    }

}
