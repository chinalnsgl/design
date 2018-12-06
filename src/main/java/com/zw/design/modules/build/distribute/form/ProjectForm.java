package com.zw.design.modules.build.distribute.form;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectForm {

    private Integer id;
    private String name;
    private String demander;
    private String address;
    private String codeSpecial;
    private String comment;
    private Integer orderNo;
    private Integer num;
    private Date planTime;
    private Integer machine;
    private Integer machineNo;
    private Integer hypre;
    private Integer hypreNo;
    private Integer electric;
    private Integer electricNo;
    private Integer software;
    private Integer softwareNo;

}
