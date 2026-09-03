package com.faniherfeei.demo1dashboard.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.util.List;
public record RowDto(
        @NotBlank String name,
        @NotEmpty List<@NotNull BigDecimal> values
) {}