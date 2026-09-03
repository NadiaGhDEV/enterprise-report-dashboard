package com.faniherfeei.demo1dashboard.service;

import com.faniherfeei.demo1dashboard.dto.*;
import com.faniherfeei.demo1dashboard.exception.NotFoundException;
import com.faniherfeei.demo1dashboard.mapper.AccountMapper;
import com.faniherfeei.demo1dashboard.mapper.DepartmentMapper;
import com.faniherfeei.demo1dashboard.model.Account;
import com.faniherfeei.demo1dashboard.model.Department;
import com.faniherfeei.demo1dashboard.model.UserPrincipal;
import com.faniherfeei.demo1dashboard.repository.AccountRepository;
import com.faniherfeei.demo1dashboard.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final DepartmentRepository departmentRepository;

    private final PasswordEncoder passwordEncoder;

    public String verify(LoginRequestDto dto) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        assert principal != null;
        return jwtService.generateToken(principal.getAccount());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDto registerAccount(AccountRequestDto dto) {
        Account account = AccountMapper.toEntity(dto);
        account.setPassword(passwordEncoder.encode(dto.password()));
        if (dto.departmentIds() != null && !dto.departmentIds().isEmpty()) {
            Set<Department> departments = new HashSet<>(departmentRepository.findAllById(dto.departmentIds()));
            if (departments.size() != dto.departmentIds().size()) {
                throw new NotFoundException("Department not found");
            }
            account.setPermissions(departments);
        }
        accountRepository.save(account);
        return AccountMapper.toResponseDto(account);
    }

    @PreAuthorize("hasAnyRole('MANAGER','USER')")
    public List<DepartmentResponseDto> getMyDepartments(Account account) {
        return account.getPermissions().stream().map(DepartmentMapper::toResponseDto).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AccountResponseDto grantDepartmentAccess(Account account, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("Department not found"));
        account.getPermissions().add(department);
        accountRepository.save(account);
        return AccountMapper.toResponseDto(account);
    }

    public AccountResponseDto getProfile(Account account) {
        return accountRepository.findByIdWithPermissions(account.getUserId())
                .map(AccountMapper::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Account not found: "));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void revokeDepartmentAccess(Account account, Long departmentId) {
        account.getPermissions().removeIf(d -> d.getDepartmentId().equals(departmentId));
        AccountMapper.toResponseDto(account);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void changePassword(ChangePasswordRequestDto dto) {
        Account account = accountRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        account.setPassword(passwordEncoder.encode(dto.newPassword()));

        accountRepository.save(account);
    }

}