package com.zw.design.modules.build.distributedesigntask.form;

import lombok.Data;

@Data
public class ProjectSendForm {

    private Integer projectId;
    private Integer machine;
    private Integer machineNo;
    private Integer hypre;
    private Integer hypreNo;
    private Integer electric;
    private Integer electricNo;
    private Integer software;
    private Integer softwareNo;

}
