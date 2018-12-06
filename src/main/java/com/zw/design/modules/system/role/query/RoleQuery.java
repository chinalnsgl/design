package com.zw.design.modules.system.role.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String roleName; // 角色名
}
