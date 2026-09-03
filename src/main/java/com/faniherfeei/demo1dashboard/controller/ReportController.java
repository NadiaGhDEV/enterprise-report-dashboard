package com.faniherfeei.demo1dashboard.controller;

import com.faniherfeei.demo1dashboard.dto.ReportRequestDto;
import com.faniherfeei.demo1dashboard.dto.ReportResponseDto;
import com.faniherfeei.demo1dashboard.model.UserPrincipal;
import com.faniherfeei.demo1dashboard.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")

public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDto> create(@AuthenticationPrincipal UserPrincipal currentUser, @Valid @RequestBody ReportRequestDto dto) {
        ReportResponseDto response = reportService.create(currentUser.getAccount(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> searchReport(@AuthenticationPrincipal UserPrincipal currentUser, @PathVariable Long id) {
        return ResponseEntity.ok(reportService.getById(currentUser.getAccount(), id));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getAll() {
        return ResponseEntity.ok(reportService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponseDto> update(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id,
            @Valid @RequestBody ReportRequestDto dto) {
        ReportResponseDto response = reportService.update(currentUser.getAccount(), id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal currentUser, @PathVariable Long id) {
        reportService.delete(currentUser.getAccount(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myReports")
    public ResponseEntity<List<ReportResponseDto>> getMyReports(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(reportService.getUserReports(currentUser.getAccount()));
    }

    @GetMapping("/getReports")
    public ResponseEntity<List<ReportResponseDto>> getDepartmentReports(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(reportService.getDepartmentReports(currentUser.getAccount()));
    }

}