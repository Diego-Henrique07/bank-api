package com.diego.bank_api.service;
import com.diego.bank_api.entity.Account;
import com.diego.bank_api.entity.Customer;
import com.diego.bank_api.entity.enums.*;
import com.diego.bank_api.exception.ForbiddenException;
import com.diego.bank_api.exception.ResourceNotFoundException;
import com.diego.bank_api.repository.AccountRepository;
import com.diego.bank_api.repository.CustomerRepository;
import com.diego.bank_api.dto.account.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request){

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not found"));

        if(customer.getStatus() != CustomerStatus.ACTIVE){
            throw new ForbiddenException("Client must be active");
        }

        Account account = new Account();
        account.setAccountType(request.accountType());
        account.setCustomer(customer);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setAgencyNumber("0001");

        Account savedAccount = accountRepository.save(account);

        return toResponse(savedAccount);
    }

    public AccountResponse findAccountById(Long accountId){

        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found"));

        return toResponse(account);
    }

    public List<AccountResponse> findAllAccount(){
        return accountRepository.findAll()
                .stream()
                .map(this :: toResponse)
                .toList();
    }

    private AccountResponse toResponse(Account account){
         return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAgencyNumber(),
                account.getBalance(),
                account.getAccountType(),
                account.getStatus(),
                account.getCreatedAt(),
                account.getCustomer().getId()
        );
    }
}
