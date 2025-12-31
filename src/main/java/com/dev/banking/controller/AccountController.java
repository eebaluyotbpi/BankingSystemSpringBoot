package com.dev.banking.controller;


import com.dev.banking.dto.AccountDto;
import com.dev.banking.entity.Account;
import com.dev.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Add account rest API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }


    // Get account rest API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return  ResponseEntity.ok(accountDto);
    }

    // Deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request ){
      Double amount = request.get("amount");
      AccountDto accountDto =   accountService.deposit(id, request.get("amount"));
      return  ResponseEntity.ok(accountDto);
    }

    // Withdraw REST APi
    @PutMapping("/{id}/withdraw")
   public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request ){
        double amount = request.get("amount");
        AccountDto accountDto =   accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
   }

   // Get all account rest api
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accountDtos = accountService.getAllAccounts();

        return ResponseEntity.ok(accountDtos);
    }
}
