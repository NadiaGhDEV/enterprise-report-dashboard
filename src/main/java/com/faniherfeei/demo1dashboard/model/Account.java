package com.faniherfeei.demo1dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "dashboard")
@Getter
@Setter
public class Account {

    @Id
    @Column(nullable = false,unique = true)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_department_permissions",
            schema = "dashboard",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )

    private Set<Department> permissions=new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "account")
    private List<Report> reports=new ArrayList<>();

}