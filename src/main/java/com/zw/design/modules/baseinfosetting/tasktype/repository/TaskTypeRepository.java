package com.zw.design.modules.baseinfosetting.tasktype.repository;

import com.zw.design.modules.baseinfosetting.tasktype.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskTypeRepository extends JpaRepository<TaskType, Integer>, JpaSpecificationExecutor<TaskType> {

    TaskType findByNameAndStatus(String name, Integer status);

    List<TaskType> findByStatus(Integer status);
}
