package com.zw.design.mapper.dbo;

import com.zw.design.entity.dbo.SysRolePermissions;
import com.zw.design.entity.dbo.SysRolePermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionsMapper {
    long countByExample(SysRolePermissionsExample example);

    int deleteByExample(SysRolePermissionsExample example);

    int insert(SysRolePermissions record);

    int insertSelective(SysRolePermissions record);

    List<SysRolePermissions> selectByExample(SysRolePermissionsExample example);

    int updateByExampleSelective(@Param("record") SysRolePermissions record, @Param("example") SysRolePermissionsExample example);

    int updateByExample(@Param("record") SysRolePermissions record, @Param("example") SysRolePermissionsExample example);
}