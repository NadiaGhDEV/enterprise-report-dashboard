package com.faniherfeei.demo1dashboard.mapper;

import com.faniherfeei.demo1dashboard.dto.AccountRequestDto;
import com.faniherfeei.demo1dashboard.dto.AccountResponseDto;
import com.faniherfeei.demo1dashboard.model.Account;

import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountResponseDto toResponseDto(Account account) {
        return new AccountResponseDto(
                account.getUserId(),
                account.getFirstName(),
                account.getLastName(),
                account.getUsername(),
                account.getRole(),
                account.getPermissions().stream().map(DepartmentMapper::toResponseDto).collect(Collectors.toSet()));
    }

    public static Account toEntity(AccountRequestDto account) {
        Account accountEntity = new Account();
        accountEntity.setUserId(account.userId());
        accountEntity.setFirstName(account.firstName());
        accountEntity.setLastName(account.lastName());
        accountEntity.setUsername(account.username());
        accountEntity.setRole(account.role());
        return accountEntity;
    }


}