package com.faniherfeei.demo1dashboard.dto;

import com.faniherfeei.demo1dashboard.model.RowData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReportResponseDto(
        Long id,
        String title,
        String description,
        Long departmentId,
        String departmentName,
        List<String> columns,
        List<RowDto> rows,
        LocalDateTime createdAt
) {

}