package com.faniherfeei.demo1dashboard.controller;

import com.faniherfeei.demo1dashboard.dto.LoginRequestDto;
import com.faniherfeei.demo1dashboard.dto.LoginResponseDto;
import com.faniherfeei.demo1dashboard.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto account) {
        return new LoginResponseDto(accountService.verify(account));
    }
}