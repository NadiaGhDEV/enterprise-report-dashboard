package com.faniherfeei.demo1dashboard.repository;

import com.faniherfeei.demo1dashboard.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByDepartment_DepartmentIdIn(Set<Long> departmentIds);
}