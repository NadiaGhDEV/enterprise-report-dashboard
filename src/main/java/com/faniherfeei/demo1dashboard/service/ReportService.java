package com.faniherfeei.demo1dashboard.service;

import com.faniherfeei.demo1dashboard.exception.NotFoundException;
import com.faniherfeei.demo1dashboard.model.Account;
import com.faniherfeei.demo1dashboard.model.Report;
import com.faniherfeei.demo1dashboard.repository.ReportRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.faniherfeei.demo1dashboard.dto.ReportRequestDto;
import com.faniherfeei.demo1dashboard.dto.ReportResponseDto;
import com.faniherfeei.demo1dashboard.mapper.ReportMapper;
import com.faniherfeei.demo1dashboard.model.Department;
import com.faniherfeei.demo1dashboard.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final DepartmentRepository departmentRepository;


    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ReportResponseDto create(Account account, ReportRequestDto dto) {
        Department department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new NotFoundException("Department not found"));

        if (!checkDepartmentAccess(account, dto.departmentId())) {
            throw new AccessDeniedException("access denied");
        }

        Report report = ReportMapper.toEntity(account, dto, department);
        Report saved = reportRepository.save(report);
        return ReportMapper.toResponseDto(saved);
    }

    private Report getOwnedReportOrThrow(Account account, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        if (!report.getAccount().getUserId().equals(account.getUserId())) {
            throw new AccessDeniedException("access denied");
        }
        return report;
    }


    public ReportResponseDto getById(Account account, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        boolean hasAccess = switch (account.getRole()) {
            case ADMIN -> true;
            case MANAGER -> checkDepartmentAccess(account, report.getDepartment().getDepartmentId());
            case USER -> report.getAccount().getUserId().equals(account.getUserId());
        };

        if (!hasAccess) throw new AccessDeniedException("Access denied");
        return ReportMapper.toResponseDto(report);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ReportResponseDto> getAll() {
        return reportRepository.findAll()
                .stream()
                .map(ReportMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ReportResponseDto update(Account account,Long id,ReportRequestDto dto) {
        Report report = getOwnedReportOrThrow(account, id);

        Department department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new NotFoundException(
                        "Department with id " + dto.departmentId() + " not found"));

        if (!department.isActive()) {
            throw new IllegalStateException("Department is not active");
        }

        if (!checkDepartmentAccess(account, dto.departmentId())) {
            throw new AccessDeniedException("access denied to target department");
        }

        report.setTitle(dto.title());
        report.setDescription(dto.description());
        report.setColumns(dto.columns());
        report.setRows(ReportMapper.toRowDataList(dto.rows()));
        report.setDepartment(department);

        Report updatedReport = reportRepository.save(report);
        return ReportMapper.toResponseDto(updatedReport);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    public void delete(Account account, Long id) {
        Report report = getOwnedReportOrThrow(account, id);
        reportRepository.delete(report);
    }

    @PreAuthorize("hasRole('MANAGER')")
    public List<ReportResponseDto> getDepartmentReports(Account account) {
        Set<Long> departmentIds = new HashSet<>();
        account.getPermissions().forEach(department -> departmentIds.add(department.getDepartmentId()));
        return reportRepository.findByDepartment_DepartmentIdIn(departmentIds).stream().map(ReportMapper::toResponseDto).toList();
    }

    @PreAuthorize("hasRole('USER')")
    public List<ReportResponseDto> getUserReports(Account account) {
        return account.getReports().stream().map(ReportMapper::toResponseDto).toList();
    }

    public boolean checkDepartmentAccess(Account account, Long departmentId) {
        return account.getPermissions().stream().anyMatch(d -> d.getDepartmentId().equals(departmentId));
    }
}