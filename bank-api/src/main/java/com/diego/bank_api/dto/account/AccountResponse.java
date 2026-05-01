package com.diego.bank_api.dto.account;
import com.diego.bank_api.entity.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        String agencyNumber,
        BigDecimal balance,
        AccountType accountType,
        AccountStatus status,
        LocalDateTime createdAt,
        Long customerId
){}
