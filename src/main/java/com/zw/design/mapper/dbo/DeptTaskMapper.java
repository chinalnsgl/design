package com.zw.design.mapper.dbo;

import com.zw.design.entity.dbo.DeptTask;
import com.zw.design.entity.dbo.DeptTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeptTaskMapper {
    long countByExample(DeptTaskExample example);

    int deleteByExample(DeptTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeptTask record);

    int insertSelective(DeptTask record);

    List<DeptTask> selectByExample(DeptTaskExample example);

    DeptTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeptTask record, @Param("example") DeptTaskExample example);

    int updateByExample(@Param("record") DeptTask record, @Param("example") DeptTaskExample example);

    int updateByPrimaryKeySelective(DeptTask record);

    int updateByPrimaryKey(DeptTask record);
}