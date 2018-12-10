package com.zw.design.modules.baseinfosetting.dept.service;

import com.zw.design.modules.baseinfosetting.dept.entity.Department;

import java.util.List;

public interface DeptService {

    // 查询所有部门
    List<Department> findAllByStatus();

    // 按ID查询部门
    Department findDeptById(Integer id);

    // 按名称查询部门
    Department findByNameAndStatus(String name, Integer status);

    // 保存部门
    Department saveDept(Department department);

    // 修改部门
    Department updateDept(Department department);

    // 修改部门状态
    Department updateDeptStatus(Integer id, Integer status);


}
