package com.zw.design.modules.baseinfosetting.dept.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.baseinfosetting.dept.entity.Department;
import com.zw.design.modules.baseinfosetting.dept.service.DeptService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/baseinfo/dept")
public class DeptController {

    private String prefix = "baseinfo/dept";

    @Autowired
    private DeptService deptService;


    /**
     * 部门页面
     */
    @GetMapping("/page")
    @RequiresRoles({"admin"})
    public String depts() {
        return prefix + "/list";
    }
    /**
     * 获取所有部门
     */
    @ResponseBody
    @GetMapping("/all")
    @RequiresRoles({"admin"})
    public List<Department> findAllDept() {
        return deptService.findAllByStatus();
    }


    /**
     * 删除部门， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateDeptStatus(@RequestParam("id")Integer id) {
        Department department = deptService.updateDeptStatus(id, 0);
        return BaseResponse.toResponse(department);
    }

    /**
     * 创建部门页面
     */
    @GetMapping("/create")
    @RequiresRoles({"admin"})
    public String deptCreate() {
        return  prefix + "/create";
    }
    /**
     * 部门唯一验证
     */
    @ResponseBody
    @PostMapping("/checkDeptNameUnique")
    @RequiresRoles({"admin"})
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
    @GetMapping("/create/{id}")
    @RequiresRoles({"admin"})
    public String deptCreate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/create";
    }
    /**
     * 保存部门
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public BaseResponse deptSave(Department department) {
        Department dept = deptService.saveDept(department);
        return BaseResponse.toResponse(dept);
    }

    /**
     * 修改部门
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresRoles({"admin"})
    public BaseResponse deptUpdate(Department department) {
        Department dept = deptService.updateDept(department);
        return BaseResponse.toResponse(dept);
    }

}
