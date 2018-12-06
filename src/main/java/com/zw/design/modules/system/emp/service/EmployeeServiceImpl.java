package com.zw.design.modules.system.emp.service;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.modules.system.dept.entity.Department;
import com.zw.design.modules.system.emp.entity.Employee;
import com.zw.design.modules.system.emp.query.EmployeeQuery;
import com.zw.design.modules.system.dept.repository.DeptRepository;
import com.zw.design.modules.system.emp.repository.EmployeeRepository;
import com.zw.design.modules.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private LogService logService;

    // 按条件查询员工返回基础表格模型数据
    @Override
    public BaseDataTableModel<Employee> findEmployeeByQuery(EmployeeQuery query) {
        // 分页排序
        Pageable pageable = PageRequest.of(query.getStart()/query.getLength(), query.getLength(), Sort.by(Sort.Direction.ASC,"id"));
        // 分页数据
        Page<Employee> empPage = employeeRepository.findAll((Specification<Employee>) (root, criteriaQuery, criteriaBuilder) -> {
            // 构建查询条件
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
        // 封装基础表格模型数据
        BaseDataTableModel<Employee> baseDataTableModel = new BaseDataTableModel<>();
        baseDataTableModel.setDraw(query.getDraw());
        baseDataTableModel.setData(empPage.getContent());
        baseDataTableModel.setRecordsTotal((int)empPage.getTotalElements());
        baseDataTableModel.setRecordsFiltered((int)empPage.getTotalElements());
        return baseDataTableModel;
    }

    // 按ID查询员工
    @Override
    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    // 按部门ID查询员工
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

    // 保存员工
    @Override
    public Employee saveEmployee(Employee employee) {
        logService.saveLog("新建员工：", employee.getName());
        return employeeRepository.save(employee);
    }

    // 修改员工
    @Override
    public Employee updateEmployee(Employee employee) {
        Employee emp = findEmployeeById(employee.getId());
        logService.saveLog("修改员工：", emp, employee);
        emp.setName(employee.getName());
        emp.setCode(employee.getCode());
        emp.setDepartment(employee.getDepartment());
        return employeeRepository.save(emp);
    }

    // 修改员工状态
    @Override
    public Employee updateEmployeeStatus(Integer id, Integer status) {
        Employee employee = findEmployeeById(id);
        logService.saveLog("删除员工：",  employee.getName());
        employee.setStatus(status);
        return employeeRepository.save(employee);
    }


}
