package com.zw.design.modules.baseinfosetting.emp.repository;

import com.zw.design.modules.baseinfosetting.emp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<Employee> findAllByStatus(Integer status);
}
