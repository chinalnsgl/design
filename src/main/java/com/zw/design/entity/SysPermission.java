package com.zw.design.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class SysPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String permissionName;

    @NotNull
    private String url;

    private String description;

    private Integer status = 1;

    @Transient
    private boolean checkFlag = false;

    @ManyToOne
    @JoinColumn(name = "pid")
    @JsonIgnoreProperties("children")
    private SysPermission parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Where(clause = "status = 1")
    @JsonIgnoreProperties("parent")
    private List<SysPermission> children;
}
