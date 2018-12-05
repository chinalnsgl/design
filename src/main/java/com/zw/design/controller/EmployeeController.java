package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.Department;
import com.zw.design.entity.Employee;
import com.zw.design.query.EmployeeQuery;
import com.zw.design.service.EmployeeService;
import com.zw.design.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class EmployeeController {

    private String prefix = "system";

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LogService logService;

    /**
     * 员工页面
     */
    @GetMapping("/emps")
    @RequiresPermissions({"emp:list"})
    public String emps() {
        return prefix + "/emp/list";
    }
    /**
     * 员工列表数据
     */
    @ResponseBody
    @PostMapping("/emp/list")
    @RequiresPermissions({"emp:list"})
    public BaseResponse empList(EmployeeQuery query) {
        DataTablesCommonDto<Employee> dto = employeeService.findEmployeeByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }


    /**
     * 创建员工页面
     */
    @GetMapping("/emp/create")
    @RequiresPermissions({"emp:create"})
    public String empCreate() {
        return  prefix + "/emp/create";
    }
    /**
     * 保存员工
     */
    @ResponseBody
    @PostMapping("/emp/save")
    @RequiresPermissions({"emp:create"})
    public BaseResponse empSave(Employee employee, @RequestParam("deptId") Integer deptId, HttpServletRequest request) {
        Department department = new Department();
        department.setId(deptId);
        employee.setDepartment(department);
        Employee emp = employeeService.saveEmployee(employee);
        logService.saveLog("新建员工：" + employee.getName(), request);
        return BaseResponse.toResponse(emp);
    }


    /**
     * 员工编辑页面
     */
    @GetMapping("/emp/edit/{id}")
    @RequiresPermissions({"emp:edit"})
    public String empEdit(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("emp", employee);
        return prefix + "/emp/edit";
    }
    /**
     * 所有部门，选中员工所在部门
     */
    @ResponseBody
    @GetMapping("emp/dept/{id}")
    @RequiresPermissions({"emp:edit"})
    public List<Department> rolePermissions(@PathVariable Integer id) {
        return employeeService.findDeptByEmpId(id);
    }
    /**
     * 修改员工
     */
    @ResponseBody
    @PostMapping("/emp/update")
    @RequiresPermissions({"emp:edit"})
    public BaseResponse empUpdate(Employee employee, @RequestParam("deptId") Integer deptId, HttpServletRequest request) {
        Department department = new Department();
        department.setId(deptId);
        employee.setDepartment(department);
        Employee emp = employeeService.updateEmployee(employee);
        logService.saveLog("修改员工：" + employee.getName(), request);
        return BaseResponse.toResponse(emp);
    }

    /**
     * 删除员工， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/emp/status")
    @RequiresPermissions({"emp:del"})
    public BaseResponse updateEmpStatus(@RequestParam("id")Integer id,  HttpServletRequest request) {
        Employee employee = employeeService.updateEmployeeStatus(id, 0);
        logService.saveLog("删除员工：" + employee.getName(), request);
        return BaseResponse.toResponse(employee);
    }
}
