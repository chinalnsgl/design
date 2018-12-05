package com.zw.design.service.impl;

import com.zw.design.entity.Department;
import com.zw.design.repository.DeptRepository;
import com.zw.design.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Department updateDeptStatus(Integer id, Integer status) {
        Department department = deptRepository.findById(id).get();
        department.setStatus(status);
        return deptRepository.save(department);
    }

    @Override
    public Department saveDept(Department department) {
        if (department.getParent() != null && department.getParent().getId() == null) {
            department.setParent(null);
        }
        return deptRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return deptRepository.findAllByStatus(1);
    }

    @Override
    public Department findDeptById(Integer id) {
        return deptRepository.findById(id).get();
    }

    @Override
    public Department updateDept(Department dept) {
        Department department = deptRepository.findById(dept.getId()).get();
        department.setDeptName(dept.getDeptName());
        return deptRepository.save(department);
    }
}
