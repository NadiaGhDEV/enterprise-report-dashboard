package com.faniherfeei.demo1dashboard.service;

import com.faniherfeei.demo1dashboard.dto.DepartmentRequestDto;
import com.faniherfeei.demo1dashboard.dto.DepartmentResponseDto;
import com.faniherfeei.demo1dashboard.mapper.DepartmentMapper;
import com.faniherfeei.demo1dashboard.model.Department;
import com.faniherfeei.demo1dashboard.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentRequestDto dto) {
        Department department = new Department();
        department.setDepartmentName(dto.departmentName());
        return DepartmentMapper.toResponseDto(departmentRepository.save(department));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void disableDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        department.setActive(false);
        departmentRepository.save(department);
    }
}