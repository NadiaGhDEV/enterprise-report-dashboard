package com.faniherfeei.demo1dashboard.repository;


import com.faniherfeei.demo1dashboard.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.permissions WHERE a.username = :username")
    Optional<Account> findByUsernameWithPermissions(String username);

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.permissions WHERE a.userId = :id")
    Optional<Account> findByIdWithPermissions(@Param("id") String id);
}