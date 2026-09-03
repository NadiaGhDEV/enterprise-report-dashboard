package com.faniherfeei.demo1dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "dashboard")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long departmentId;

    @Column(nullable = false, unique = true)
    private String departmentName;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "department")
    private List<Report> reports;

}