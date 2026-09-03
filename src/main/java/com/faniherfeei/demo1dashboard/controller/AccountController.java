package com.faniherfeei.demo1dashboard.controller;

import com.faniherfeei.demo1dashboard.dto.AccountResponseDto;
import com.faniherfeei.demo1dashboard.dto.DepartmentResponseDto;
import com.faniherfeei.demo1dashboard.model.UserPrincipal;
import com.faniherfeei.demo1dashboard.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController{

    private final AccountService accountService;

    @GetMapping("/profile")
    public ResponseEntity<AccountResponseDto> getMyProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(accountService.getProfile(currentUser.getAccount()));
    }

    @GetMapping("/myDepartments")
    public ResponseEntity<List<DepartmentResponseDto>> getMyDepartments(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(accountService.getMyDepartments(currentUser.getAccount()));
    }
}