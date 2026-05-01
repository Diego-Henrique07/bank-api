package com.diego.bank_api.controller;
import com.diego.bank_api.service.TransactionService;
import com.diego.bank_api.dto.transaction.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Transactional
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest depositRequest){
        TransactionResponse response = transactionService.deposit(depositRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Transactional
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest){
        TransactionResponse response = transactionService.withdraw(withdrawRequest);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest transferRequest){
        TransactionResponse response = transactionService.transfer(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
