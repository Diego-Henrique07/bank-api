package com.diego.bank_api.controller;
import com.diego.bank_api.dto.account.*;
import com.diego.bank_api.service.AccountService;
import com.diego.bank_api.dto.transaction.TransactionResponse;
import com.diego.bank_api.service.TransactionService;


import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @Transactional
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount( @Valid @RequestBody AccountCreateRequest request){

        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findAccountById(@PathVariable Long accountId){
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAllAccounts(){
        return ResponseEntity.ok(accountService.findAllAccount());
    }

    @GetMapping("/{id}/statement")
    public ResponseEntity<List<TransactionResponse>> statement(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(transactionService.getStatementByAccountId(accountId));
    }

}
