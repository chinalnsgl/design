package com.zw.design.mapper.dbo;

import com.zw.design.entity.dbo.SysUserRoles;
import com.zw.design.entity.dbo.SysUserRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserRolesMapper {
    long countByExample(SysUserRolesExample example);

    int deleteByExample(SysUserRolesExample example);

    int insert(SysUserRoles record);

    int insertSelective(SysUserRoles record);

    List<SysUserRoles> selectByExample(SysUserRolesExample example);

    int updateByExampleSelective(@Param("record") SysUserRoles record, @Param("example") SysUserRolesExample example);

    int updateByExample(@Param("record") SysUserRoles record, @Param("example") SysUserRolesExample example);
}