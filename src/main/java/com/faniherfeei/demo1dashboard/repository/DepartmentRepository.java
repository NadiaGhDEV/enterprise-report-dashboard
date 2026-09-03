package com.faniherfeei.demo1dashboard.repository;

import com.faniherfeei.demo1dashboard.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
