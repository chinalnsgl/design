package com.zw.design.modules.baseinfosetting.sectiontype.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zw.design.modules.baseinfosetting.section.entity.Section;
import com.zw.design.modules.baseinfosetting.taskname.entity.TaskName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 科室类型表
 */
@Entity
@Getter
@Setter
public class SectionType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name; // 名称

    private String code; // 编号

    private int status = 1; // 基础状态 0：删除  1：正常

    private Integer orderNo = 1; // 排序（保留字段）

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime = new Date(); // 创建时间

    @OneToMany(mappedBy = "sectionType")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("sectionType")
    private List<Section> sections;

    @OneToMany(mappedBy = "sectionType")
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("sectionType")
    private List<TaskName> taskNames;

}
