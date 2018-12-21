package com.zw.design.modules.lookboard.single.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleMapper {

    Float findProjectTasksCompleteStatus(@Param("id") Integer id);

    Float findDesignTasksCompleteStatus(@Param("id") Integer id);

    Float findProcessTasksCompleteStatus(@Param("id") Integer id);

    Float findProduceTasksCompleteStatus(@Param("id") Integer id);
}
