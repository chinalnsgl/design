package com.zw.design.service;

import com.zw.design.entity.Department;

import java.util.List;

public interface DeptService {

    Department updateDeptStatus(Integer id, Integer status);

    Department saveDept(Department department);

    List<Department> findAll();

    Department updateDept(Department department);

    Department findDeptById(Integer id);

}
