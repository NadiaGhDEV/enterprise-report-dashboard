package com.faniherfeei.demo1dashboard.controller;

import com.faniherfeei.demo1dashboard.dto.*;
import com.faniherfeei.demo1dashboard.model.UserPrincipal;
import com.faniherfeei.demo1dashboard.service.AccountService;
import com.faniherfeei.demo1dashboard.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Validated
public class AdminAccountController {

    private final AccountService accountService;

    private final DepartmentService departmentService;

    @PostMapping("/addAccount")
    public AccountResponseDto createAccount(@Valid @RequestBody AccountRequestDto account) {
        return accountService.registerAccount(account);
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PostMapping("/department/addDepartment")
    public ResponseEntity<DepartmentResponseDto> addDepartment(@Valid @RequestBody DepartmentRequestDto department) {
        DepartmentResponseDto departmentResponse = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponse);
    }

    @PutMapping("/department/{id}/disable")
    public ResponseEntity<DepartmentResponseDto> disableDepartment(@PathVariable Long id) {
        departmentService.disableDepartment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/department/allDepartments")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping("/permissions/{departmentId}")
    public ResponseEntity<AccountResponseDto> grantDepartmentAccess(@AuthenticationPrincipal UserPrincipal currentUser, @PathVariable Long departmentId) {
        AccountResponseDto account = accountService.grantDepartmentAccess(currentUser.getAccount(),departmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @DeleteMapping("/permissions/{departmentId}")
    public ResponseEntity<Void> revokeDepartmentAccess(
            @AuthenticationPrincipal UserPrincipal currentUser, @PathVariable Long departmentId) {
        accountService.revokeDepartmentAccess(currentUser.getAccount(), departmentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changePass")
    public ResponseEntity<Void> changePass(@Valid @RequestBody ChangePasswordRequestDto request) {
        accountService.changePassword(request);
        return ResponseEntity.ok().build();
    }

}