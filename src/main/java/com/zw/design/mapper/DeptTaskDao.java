package com.zw.design.mapper;

import com.zw.design.dto.DeptTaskDto;
import com.zw.design.dto.TaskDto;
import com.zw.design.query.ProjectQuery;
import com.zw.design.query.TaskQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeptTaskDao {

    List<TaskDto> findTasks(ProjectQuery query);

    Integer findTasksCount(ProjectQuery query);

    Integer findMaxStepNumByProjectId(@Param("id") Integer id);

    List<Integer> findIdsByDept(TaskQuery query);

    List<DeptTaskDto> findDeptTaskList(TaskQuery query);

    Integer findDeptTaskCount(TaskQuery query);
}
