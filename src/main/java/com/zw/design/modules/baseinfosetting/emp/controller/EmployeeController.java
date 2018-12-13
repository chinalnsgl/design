package com.zw.design.modules.baseinfosetting.emp.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.dept.entity.Department;
import com.zw.design.modules.baseinfosetting.emp.entity.Employee;
import com.zw.design.modules.baseinfosetting.emp.query.EmployeeQuery;
import com.zw.design.modules.baseinfosetting.emp.service.EmployeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/baseinfo/emp")
public class EmployeeController {

    private String prefix = "baseinfo/emp";

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工页面
     */
    @GetMapping("/page")
    @RequiresRoles({"admin"})
    public String emps() {
        return prefix + "/list";
    }
    /**
     * 员工列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresRoles({"admin"})
    public BaseResponse empList(EmployeeQuery query) {
        BaseDataTableModel<Employee> dto = employeeService.findEmployeeByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }


    /**
     * 创建员工页面
     */
    @GetMapping("/create")
    @RequiresRoles({"admin"})
    public String empCreate() {
        return  prefix + "/create";
    }
    /**
     * 保存员工
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public BaseResponse empSave(Employee employee, @RequestParam("deptId") Integer deptId) {
        Department department = new Department();
        department.setId(deptId);
        employee.setDepartment(department);
        Employee emp = employeeService.saveEmployee(employee);
        return BaseResponse.toResponse(emp);
    }


    /**
     * 员工编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresRoles({"admin"})
    public String empEdit(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("emp", employee);
        return prefix + "/edit";
    }
    /**
     * 所有部门，选中员工所在部门
     */
    @ResponseBody
    @GetMapping("/dept/{id}")
    @RequiresRoles({"admin"})
    public List<Department> rolePermissions(@PathVariable Integer id) {
        return employeeService.findDeptByEmpId(id);
    }
    /**
     * 修改员工
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresRoles({"admin"})
    public BaseResponse empUpdate(Employee employee, @RequestParam("deptId") Integer deptId, HttpServletRequest request) {
        Department department = new Department();
        department.setId(deptId);
        employee.setDepartment(department);
        Employee emp = employeeService.updateEmployee(employee);
        return BaseResponse.toResponse(emp);
    }

    /**
     * 删除员工， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresRoles({"admin"})
    public BaseResponse updateEmpStatus(@RequestParam("id")Integer id,  HttpServletRequest request) {
        Employee employee = employeeService.updateEmployeeStatus(id, 0);
        return BaseResponse.toResponse(employee);
    }
}
