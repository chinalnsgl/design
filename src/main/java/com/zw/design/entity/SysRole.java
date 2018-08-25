package com.zw.design.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String roleName;

    private Integer status = 1;

    @Transient
    private boolean checkFlag = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @Where(clause = "status = 1")
    private List<SysPermission> permissions;
}
