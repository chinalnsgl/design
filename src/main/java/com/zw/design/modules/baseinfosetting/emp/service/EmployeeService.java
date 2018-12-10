package com.zw.design.modules.baseinfosetting.emp.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.baseinfosetting.dept.entity.Department;
import com.zw.design.modules.baseinfosetting.emp.entity.Employee;
import com.zw.design.modules.baseinfosetting.emp.query.EmployeeQuery;

import java.util.List;

public interface EmployeeService {

    // 按条件查询员工返回基础table数据
    BaseDataTableModel<Employee> findEmployeeByQuery(EmployeeQuery query);

    // 按ID查询员工
    Employee findEmployeeById(Integer id);

    // 按部门ID查询员工
    List<Department> findDeptByEmpId(Integer id);

    // 保存员工
    Employee saveEmployee(Employee employee);

    // 修改员工
    Employee updateEmployee(Employee employee);

    // 修改员工状态
    Employee updateEmployeeStatus(Integer id, Integer status);
}
