package com.dev.banking.repository;

import com.dev.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> id(Long id);
}
