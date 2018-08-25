package com.zw.design.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQuery implements Serializable {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String userName; // 帐号
    private String name; // 用户名
}
