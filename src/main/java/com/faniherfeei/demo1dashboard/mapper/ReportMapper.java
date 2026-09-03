package com.faniherfeei.demo1dashboard.mapper;

import com.faniherfeei.demo1dashboard.dto.ReportRequestDto;
import com.faniherfeei.demo1dashboard.dto.ReportResponseDto;
import com.faniherfeei.demo1dashboard.dto.RowDto;
import com.faniherfeei.demo1dashboard.model.Account;
import com.faniherfeei.demo1dashboard.model.Department;
import com.faniherfeei.demo1dashboard.model.Report;
import com.faniherfeei.demo1dashboard.model.RowData;

import java.util.List;

public class ReportMapper {

    private ReportMapper() {
    }

    public static Report toEntity(Account account, ReportRequestDto dto, Department department) {
        Report report = new Report();
        report.setTitle(dto.title());
        report.setDescription(dto.description());
        report.setAccount(account);
        report.setDepartment(department);
        report.setColumns(dto.columns());
        report.setRows(toRowDataList(dto.rows()));
        return report;
    }

    public static ReportResponseDto toResponseDto(Report report) {
        return new ReportResponseDto(
                report.getId(),
                report.getTitle(),
                report.getDescription(),
                report.getDepartment().getDepartmentId(),
                report.getDepartment().getDepartmentName(),
                report.getColumns(),
                toRowDtoList(report.getRows()),
                report.getCreatedAt()
        );
    }

    public static List<RowData> toRowDataList(List<RowDto> rows) {
        return rows.stream()
                .map(r -> new RowData(r.name(), r.values()))
                .toList();
    }

    private static List<RowDto> toRowDtoList(List<RowData> rows) {
        return rows.stream()
                .map(r -> new RowDto(r.name(), r.values()))
                .toList();
    }
}
