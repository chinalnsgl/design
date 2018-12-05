package com.zw.design.service;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.Department;
import com.zw.design.entity.Employee;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.query.EmployeeQuery;
import com.zw.design.query.RoleQuery;

import java.util.List;

public interface EmployeeService {

    DataTablesCommonDto<Employee> findEmployeeByQuery(EmployeeQuery query);

    Employee findEmployeeById(Integer id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee updateEmployeeStatus(Integer id, Integer status);

    List<Department> findDeptByEmpId(Integer id);
}
