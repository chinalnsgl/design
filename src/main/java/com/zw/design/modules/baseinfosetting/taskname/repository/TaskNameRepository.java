package com.zw.design.modules.baseinfosetting.taskname.repository;

import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskNameRepository extends JpaRepository<TaskName, Integer>, JpaSpecificationExecutor<TaskName> {

    TaskName findByNameAndStatus(String name, Integer status);

}
