package com.faniherfeei.demo1dashboard.controller;

import com.faniherfeei.demo1dashboard.dto.ReportDto;
import com.faniherfeei.demo1dashboard.dto.ReportResponseDto;
import com.faniherfeei.demo1dashboard.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> create(@Valid @RequestBody ReportDto dto) {
        ReportResponseDto response = reportService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getAll() {
        return ResponseEntity.ok(reportService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ReportDto dto) {
        ReportResponseDto response = reportService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}