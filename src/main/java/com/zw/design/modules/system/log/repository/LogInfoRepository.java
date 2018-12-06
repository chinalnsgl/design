package com.zw.design.modules.system.log.repository;

import com.zw.design.modules.system.log.entity.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogInfoRepository extends JpaRepository<LogInfo, Integer>, JpaSpecificationExecutor<LogInfo> {

}
