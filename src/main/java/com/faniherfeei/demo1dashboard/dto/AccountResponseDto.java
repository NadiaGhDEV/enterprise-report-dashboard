package com.faniherfeei.demo1dashboard.dto;

import com.faniherfeei.demo1dashboard.model.Role;

import java.util.Set;

public record AccountResponseDto(
        String userId,
        String firstName,
        String lastName,
        String username,
        Role role,
        Set<DepartmentResponseDto> permissions
) {

}