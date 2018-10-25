package com.zw.design.mapper;

import com.zw.design.dto.CollectDto;
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

    CollectDto findProjectCount(TaskQuery query);

    CollectDto findProjectDelCompCount(TaskQuery query);

    CollectDto findProjectDelUnCompCount(TaskQuery query);

    CollectDto findDeptUndoneCount(TaskQuery query);

    CollectDto findDeptdoneCount(TaskQuery query);

    List<DeptTaskDto> findProcessList(TaskQuery query);
    Integer findProcessCount(TaskQuery query);
    List<DeptTaskDto> findProduceList(TaskQuery query);
    Integer findProduceCount(TaskQuery query);

    Float findProduceCompleteStatus(@Param("id") Integer id);
    Float findDeptCompleteStatus(@Param("id") Integer id);

    int updateProcess1();
    int updateProcess2();
    int updateProcess3();
    int updateProcess4();
    int updateProcess5();


}
