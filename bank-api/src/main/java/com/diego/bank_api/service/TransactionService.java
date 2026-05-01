package com.diego.bank_api.service;
import com.diego.bank_api.dto.transaction.*;
import com.diego.bank_api.entity.*;
import com.diego.bank_api.entity.enums.*;
import com.diego.bank_api.exception.*;
import com.diego.bank_api.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponse deposit(DepositRequest depositRequest) {

        Account account = accountRepository.findById(depositRequest.targetAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Target Not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new ForbiddenException("Account is not active");
        }

        BigDecimal amount = depositRequest.amount();
        if (amount == null) {
            throw new BadRequestException("Amount is required");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must  be greater than zero");
        }

        BigDecimal oldBalance =  account.getBalance();

        account.setBalance(account.getBalance().add(amount));
        Account savedAccount = accountRepository.save(account);

        BigDecimal newBalance = savedAccount.getBalance();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(depositRequest.amount());
        transaction.setDescription("Deposit transaction");
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setSourceAccount(null);
        transaction.setTargetAccount(savedAccount);

        transaction.setSourceBalanceBefore(null);
        transaction.setSourceBalanceAfter(null);
        transaction.setTargetBalanceBefore(oldBalance);
        transaction.setTargetBalanceAfter(newBalance);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest){
        Account account = accountRepository.findById(withdrawRequest.sourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Source not found"));

        BigDecimal amount = withdrawRequest.amount();
        if (amount == null){
            throw  new BadRequestException("Amount is required");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new BadRequestException("Amount must  be greater than zero");
        }

        BigDecimal balance = account.getBalance();

        if (balance.compareTo(amount) < 0){
            throw  new BadRequestException("Insufficient balance");
        }

        BigDecimal oldBalance = account.getBalance();

        account.setBalance(account.getBalance().subtract(amount));
        Account savedAccount = accountRepository.save(account);

        BigDecimal newBalance = savedAccount.getBalance();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAmount(withdrawRequest.amount());
        transaction.setDescription("withdraw transaction");
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setSourceAccount(savedAccount);
        transaction.setTargetAccount(null);

        transaction.setSourceBalanceBefore(oldBalance);
        transaction.setSourceBalanceAfter(newBalance);
        transaction.setTargetBalanceBefore(null);
        transaction.setTargetBalanceAfter(null);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest transferRequest){
        Account sourceAccount = accountRepository.findById(transferRequest.sourceAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

        Account targetAccount = accountRepository.findById(transferRequest.targetAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Target account not found"));

        if (transferRequest.sourceAccountId().equals(transferRequest.targetAccountId())){
            throw  new BadRequestException("Source and target accounts must be different");
        }

        if (sourceAccount.getStatus() != AccountStatus.ACTIVE){
            throw new ForbiddenException("Account is not active");
        }

        if (targetAccount.getStatus() != AccountStatus.ACTIVE){
            throw new ForbiddenException("Account is not active");
        }

        BigDecimal amount = transferRequest.amount();
        if (amount == null){
            throw new BadRequestException("Amount is required");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new BadRequestException("Amount must be greater than zero");
        }

        BigDecimal balance = sourceAccount.getBalance();
        if (balance.compareTo(amount) < 0){
            throw new BadRequestException("Insufficient balance");
        }

        BigDecimal sourceOldBalance = sourceAccount.getBalance();
        BigDecimal targetOldBalance = targetAccount.getBalance();

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        Account savedSourceAccount = accountRepository.save(sourceAccount);
        Account savedTargetAccount = accountRepository.save(targetAccount);

        BigDecimal sourceNewBalance = savedSourceAccount.getBalance();
        BigDecimal targetNewBalance = savedTargetAccount.getBalance();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setDescription("Transfer transaction");
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setSourceAccount(savedSourceAccount);
        transaction.setTargetAccount(savedTargetAccount);

        transaction.setSourceBalanceBefore(sourceOldBalance);
        transaction.setSourceBalanceAfter(sourceNewBalance);
        transaction.setTargetBalanceBefore(targetOldBalance);
        transaction.setTargetBalanceAfter(targetNewBalance);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponse(savedTransaction);
    }

    public List<TransactionResponse> getStatementByAccountId(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found"));

        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId,accountId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionResponse toResponse(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getId() : null,
                transaction.getTargetAccount() != null ? transaction.getTargetAccount().getId() : null,
                transaction.getSourceBalanceBefore(),
                transaction.getSourceBalanceAfter(),
                transaction.getTargetBalanceBefore(),
                transaction.getTargetBalanceAfter()
                );
    }
}
