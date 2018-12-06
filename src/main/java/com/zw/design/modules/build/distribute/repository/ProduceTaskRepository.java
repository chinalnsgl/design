package com.zw.design.modules.build.distribute.repository;

import com.zw.design.modules.build.distribute.entity.ProduceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProduceTaskRepository extends JpaRepository<ProduceTask, Integer>, JpaSpecificationExecutor<ProduceTask> {

}
