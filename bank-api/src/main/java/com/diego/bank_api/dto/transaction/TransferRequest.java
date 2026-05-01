package com.diego.bank_api.dto.transaction;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        Long sourceAccountId,
        @NotNull
        Long targetAccountId,
        @NotNull
        BigDecimal amount,
        @NotNull
        String description
){}
