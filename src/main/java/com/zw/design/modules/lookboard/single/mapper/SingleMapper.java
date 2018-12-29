package com.zw.design.modules.lookboard.single.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleMapper {

    // 统计项目任务状态
    Float findProjectTasksCompleteStatus(@Param("id") Integer id);

    // 统计设计任务状态
    Float findDesignTasksCompleteStatus(@Param("id") Integer id);

    // 统计工艺任务状态
    Float findProcessTasksCompleteStatus(@Param("id") Integer id);

    // 统计生产任务状态
    Float findProduceTasksCompleteStatus(@Param("id") Integer id);

    // 统计科室任务状态
    Float findSectionTasksCompleteStatus(@Param("id") Integer id);
}
