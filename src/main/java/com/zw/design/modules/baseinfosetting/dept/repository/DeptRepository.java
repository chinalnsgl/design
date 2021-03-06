package com.zw.design.modules.baseinfosetting.dept.repository;

import com.zw.design.modules.baseinfosetting.dept.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DeptRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

    List<Department> findAllByStatus(Integer status);

    Department findByDeptNameAndStatus(String name, Integer status);
}
