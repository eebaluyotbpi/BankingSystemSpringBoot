package com.dev.banking.service;

import com.dev.banking.dto.AccountDto;
import com.dev.banking.entity.Account;
import com.dev.banking.method.AccountMapper;
import com.dev.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository   ) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        // Check in databse if account is existing or not
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if(account.getBalance() <  amount) {
            throw new RuntimeException("Insufficient funds");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
          List<Account> accounts = accountRepository.findAll();
          return accounts.stream().map((account -> AccountMapper.mapToAccountDto(account)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.deleteById(id);

    }

//    @Override
//    public AccountDto updateAcccount(Long id, AccountDto accountDto) {
////        Account account = accountRepository.findAll();
//        return null;
//    }
}
