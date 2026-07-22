package com.faniherfeei.demo1dashboard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ReportDto(

        @Size(max = 1000)
        String description,

        @NotBlank
        @Size(max = 1000)
        String title,

        @NotEmpty @Size(max = 14, message = "حداکثر ۱۰ ستون مجاز است")
        List<@NotBlank String> columns,


        @NotEmpty
        List<@Valid RowDto> rows,

        @NotNull
        Long departmentId

//        @NotNull
//        String departmentName
//        department name
) {
    @AssertTrue(message = "طول مقادیر هر ردیف باید برابر تعداد ستون‌ها باشد")
    public boolean isRowsValid() {
        return rows.stream().allMatch(row -> row.values().size() == columns.size());
    }
}