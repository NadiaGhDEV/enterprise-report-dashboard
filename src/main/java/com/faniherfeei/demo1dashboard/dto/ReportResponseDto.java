package com.faniherfeei.demo1dashboard.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResponseDto(
        Long id,
        String title,
        String description,
        Long departmentId,
        String departmentName,
//        String createdByUsername,
        List<String> columns,
        List<RowDto> rows,
        LocalDateTime createdAt
) {

}