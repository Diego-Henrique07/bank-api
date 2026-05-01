package com.diego.bank_api.dto.transaction;
import com.diego.bank_api.entity.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionType transactionType,
        BigDecimal amount,
        String description,
        TransactionStatus status,
        LocalDateTime createAt,
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal sourceBalanceBefore,
        BigDecimal sourceBalanceAfter,
        BigDecimal targetBalanceBefore,
        BigDecimal targetBalanceAfter
){}
