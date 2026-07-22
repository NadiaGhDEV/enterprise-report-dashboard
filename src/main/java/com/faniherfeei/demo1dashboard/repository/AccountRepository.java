package com.faniherfeei.demo1dashboard.repository;


import com.faniherfeei.demo1dashboard.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {

}
