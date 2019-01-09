package com.zw.design.modules.integrate.schedule.mapper;

import com.zw.design.modules.integrate.schedule.model.ScheduleModel;
import com.zw.design.modules.integrate.schedule.query.ScheduleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleMapper {

    List<ScheduleModel> findProjectByQuery(ScheduleQuery query);

}
