package com.faniherfeei.demo1dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "permissions",
            schema = "dashboard",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId")
    )
    private Set<Permissions> permissions;


}
