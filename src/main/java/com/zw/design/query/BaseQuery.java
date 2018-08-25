package com.zw.design.query;

import lombok.Data;

@Data
public class BaseQuery {

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
}
