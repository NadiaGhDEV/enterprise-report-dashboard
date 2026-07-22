package com.faniherfeei.demo1dashboard.service;

import com.faniherfeei.demo1dashboard.model.Report;
import com.faniherfeei.demo1dashboard.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import com.faniherfeei.demo1dashboard.dto.ReportDto;
import com.faniherfeei.demo1dashboard.dto.ReportResponseDto;
import com.faniherfeei.demo1dashboard.mapper.ReportMapper;
import com.faniherfeei.demo1dashboard.model.Department;
import com.faniherfeei.demo1dashboard.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public ReportResponseDto create(ReportDto dto) {
        Department department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "دپارتمانی با شناسه‌ی " + dto.departmentId() + " یافت نشد"));

        Report report = ReportMapper.toEntity(dto, department);
        Report saved = reportRepository.save(report);
        return ReportMapper.toResponseDto(saved);
    }

    public ReportResponseDto getById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "گزارشی با شناسه‌ی " + id + " یافت نشد"));
        return ReportMapper.toResponseDto(report);
    }

    public List<ReportResponseDto> getAll() {
        return reportRepository.findAll()
                .stream()
                .map(ReportMapper::toResponseDto)
                .toList();
    }

    @Transactional
    public ReportResponseDto update(Long id, ReportDto dto) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "گزارشی با شناسه‌ی " + id + " یافت نشد"));

        Department department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "دپارتمانی با شناسه‌ی " + dto.departmentId() + " یافت نشد"));

        report.setTitle(dto.title());
        report.setDescription(dto.description());
        report.setColumns(dto.columns());
        report.setRows(ReportMapper.toRowDataList(dto.rows()));
        report.setDepartment(department);

        Report updatedReport = reportRepository.save(report);
        return ReportMapper.toResponseDto(updatedReport);
    }

    @Transactional
    public void delete(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        reportRepository.delete(report);
    }

}
