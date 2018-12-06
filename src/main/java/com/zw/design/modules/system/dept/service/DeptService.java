package com.zw.design.modules.system.dept.service;

import com.zw.design.modules.system.dept.entity.Department;

import java.util.List;

public interface DeptService {

    Department updateDeptStatus(Integer id, Integer status);

    Department saveDept(Department department);

    List<Department> findAll();

    Department updateDept(Department department);

    Department findDeptById(Integer id);

}
