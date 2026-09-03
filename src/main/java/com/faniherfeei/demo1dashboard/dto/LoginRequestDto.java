package com.faniherfeei.demo1dashboard.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank String username,@NotBlank String password) {
}