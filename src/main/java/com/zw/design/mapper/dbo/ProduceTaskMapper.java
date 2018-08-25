package com.zw.design.mapper.dbo;

import com.zw.design.entity.dbo.ProduceTask;
import com.zw.design.entity.dbo.ProduceTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProduceTaskMapper {
    long countByExample(ProduceTaskExample example);

    int deleteByExample(ProduceTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProduceTask record);

    int insertSelective(ProduceTask record);

    List<ProduceTask> selectByExample(ProduceTaskExample example);

    ProduceTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProduceTask record, @Param("example") ProduceTaskExample example);

    int updateByExample(@Param("record") ProduceTask record, @Param("example") ProduceTaskExample example);

    int updateByPrimaryKeySelective(ProduceTask record);

    int updateByPrimaryKey(ProduceTask record);
}