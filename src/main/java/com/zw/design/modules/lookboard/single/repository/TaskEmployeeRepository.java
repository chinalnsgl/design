package com.zw.design.modules.lookboard.single.repository;

import com.zw.design.modules.lookboard.single.entity.Image;
import com.zw.design.modules.lookboard.single.entity.TaskEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskEmployeeRepository extends JpaRepository<TaskEmployee, Integer>, JpaSpecificationExecutor<TaskEmployee> {


}
