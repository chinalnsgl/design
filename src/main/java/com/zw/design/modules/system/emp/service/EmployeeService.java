package com.zw.design.modules.system.emp.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.dept.entity.Department;
import com.zw.design.modules.system.emp.entity.Employee;
import com.zw.design.modules.system.emp.query.EmployeeQuery;

import java.util.List;

public interface EmployeeService {

    BaseDataTableModel<Employee> findEmployeeByQuery(EmployeeQuery query);

    Employee findEmployeeById(Integer id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee updateEmployeeStatus(Integer id, Integer status);

    List<Department> findDeptByEmpId(Integer id);
}
