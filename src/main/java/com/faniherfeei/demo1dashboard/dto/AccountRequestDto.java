package com.faniherfeei.demo1dashboard.dto;

import com.faniherfeei.demo1dashboard.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record AccountRequestDto(
        @NotBlank String userId,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String username,
        @NotBlank @Size(min = 8, message = "Minimum length is 8 characters.")
        String password,
        @NotNull Role role,
        Set<Long> departmentIds
) {}