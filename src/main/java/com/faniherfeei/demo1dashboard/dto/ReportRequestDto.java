package com.faniherfeei.demo1dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ReportRequestDto(

        @NotBlank
        @Size(max = 500)
        String title,

        @Size(max = 1000)
        String description,



        @NotEmpty @Size(max = 14)
        List<@NotBlank String> columns,


        @NotEmpty
        List<@Valid RowDto> rows,

        @NotNull
        Long departmentId

) {

    @AssertTrue(message = "Each row's value count must match the number of columns")
    public boolean isRowsValid() {
        return rows.stream().allMatch(row -> row.values().size() == columns.size());
    }
}