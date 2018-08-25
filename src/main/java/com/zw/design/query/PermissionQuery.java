package com.zw.design.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionQuery implements Serializable {
    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private Integer pid;
    private String permissionName;
}
