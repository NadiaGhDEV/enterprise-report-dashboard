package com.faniherfeei.demo1dashboard.mapper;

import com.faniherfeei.demo1dashboard.dto.DepartmentResponseDto;
import com.faniherfeei.demo1dashboard.model.Department;


public class DepartmentMapper {


    public static DepartmentResponseDto toResponseDto(Department department){
        return new DepartmentResponseDto(
                department.getDepartmentId(),
                department.getDepartmentName()
        );
    }

}