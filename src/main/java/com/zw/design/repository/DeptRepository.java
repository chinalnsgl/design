package com.zw.design.repository;

import com.zw.design.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DeptRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

    List<Department> findAllByStatus(Integer status);
}
