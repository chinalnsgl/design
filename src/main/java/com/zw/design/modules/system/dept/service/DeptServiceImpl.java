package com.zw.design.modules.system.dept.service;

import com.zw.design.modules.system.dept.entity.Department;
import com.zw.design.modules.system.dept.repository.DeptRepository;
import com.zw.design.modules.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private LogService logService;

    @Override
    public Department updateDeptStatus(Integer id, Integer status) {
        Department department = deptRepository.findById(id).get();
        logService.saveLog("删除部门：" , department.getDeptName());
        department.setStatus(status);
        return deptRepository.save(department);
    }

    @Override
    public Department saveDept(Department department) {
        logService.saveLog("新建部门：" , department.getDeptName());
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
        logService.saveLog("修改部门：", department, dept);
        department.setDeptName(dept.getDeptName());
        return deptRepository.save(department);
    }
}
