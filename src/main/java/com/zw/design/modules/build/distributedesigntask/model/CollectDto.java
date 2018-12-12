package com.zw.design.modules.build.distributedesigntask.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CollectDto implements Serializable {

    private Integer projectNum = 0;
    private Integer productNum = 0;

}
