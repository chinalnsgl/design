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
public class SysUser implements Serializable /*implements UserDetails */{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String userName;

    private String name;

    @NotNull
    private String password;
    private Integer status = 1;

    @ManyToMany(fetch = FetchType.EAGER)
    @Where(clause = "status > 0")
    private List<SysRole> roles;
}
