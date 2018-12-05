package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.Department;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.service.DeptService;
import com.zw.design.service.LogService;
import com.zw.design.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class DeptController {

    private String prefix = "system";

    @Autowired
    private DeptService deptService;
    @Autowired
    private LogService logService;

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
        return deptService.findAll();
    }


    /**
     * 删除部门， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/dept/status")
    @RequiresPermissions({"dept:del"})
    public BaseResponse updateDeptStatus(@RequestParam("id")Integer id,  HttpServletRequest request) {
        Department department = deptService.updateDeptStatus(id, 0);
        logService.saveLog("删除部门：" + department.getDeptName(), request);
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
    public BaseResponse deptSave(Department department, HttpServletRequest request) {
        Department dept = deptService.saveDept(department);
        logService.saveLog("新建部门：" + department.getDeptName(), request);
        return BaseResponse.toResponse(dept);
    }

    /**
     * 修改部门
     */
    @ResponseBody
    @PostMapping("/dept/update")
    @RequiresPermissions({"dept:edit"})
    public BaseResponse deptUpdate(Department department, HttpServletRequest request) {
        Department dept = deptService.updateDept(department);
        logService.saveLog("修改部门：" + department.getDeptName(), request);
        return BaseResponse.toResponse(dept);
    }

}
