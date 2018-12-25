package com.zw.design.modules.integrate.performance.mapper;

import com.zw.design.modules.build.create.entity.Project;
import com.zw.design.modules.integrate.performance.query.PerformanceQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceMapper {

    List<Project> findProjectByQuery(PerformanceQuery query);

}
