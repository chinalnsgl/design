package com.zw.design.service.impl;

import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.entity.Department;
import com.zw.design.entity.Employee;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.query.EmployeeQuery;
import com.zw.design.query.RoleQuery;
import com.zw.design.repository.DeptRepository;
import com.zw.design.repository.EmployeeRepository;
import com.zw.design.repository.SysPermissionRepository;
import com.zw.design.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeptRepository deptRepository;

    @Override
    public DataTablesCommonDto<Employee> findEmployeeByQuery(EmployeeQuery query) {
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.ASC,"id"));
        Page<Employee> empPage = employeeRepository.findAll((Specification<Employee>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (null != query.getNameQuery() && !"".equals(query.getNameQuery())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getNameQuery() + "%"));
            }
            if (null != query.getCodeQuery() && !"".equals(query.getCodeQuery())) {
                list.add(criteriaBuilder.like(root.get("code").as(String.class), "%" + query.getCodeQuery() + "%"));
            }
            if (null != query.getDeptId()) {
                list.add(criteriaBuilder.equal(root.get("department").get("id"), query.getDeptId()));
            }
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        DataTablesCommonDto<Employee> dataTablesCommonDto = new DataTablesCommonDto<>();
        dataTablesCommonDto.setDraw(query.getDraw());
        dataTablesCommonDto.setData(empPage.getContent());
        dataTablesCommonDto.setRecordsTotal((int)empPage.getTotalElements());
        dataTablesCommonDto.setRecordsFiltered((int)empPage.getTotalElements());
        return dataTablesCommonDto;
    }

    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee emp = findEmployeeById(employee.getId());
        emp.setName(employee.getName());
        emp.setCode(employee.getCode());
        emp.setDepartment(employee.getDepartment());
        return employeeRepository.save(emp);
    }

    @Override
    public Employee updateEmployeeStatus(Integer id, Integer status) {
        Employee employee = findEmployeeById(id);
        employee.setStatus(status);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Department> findDeptByEmpId(Integer id) {
        List<Department> departments = deptRepository.findAllByStatus(1);
        Employee employee = findEmployeeById(id);
        for (Department department : departments) {
            if (department.getId() == employee.getDepartment().getId()) {
                department.setCheckFlag(true);
            }
        }
        return departments;
    }
}
