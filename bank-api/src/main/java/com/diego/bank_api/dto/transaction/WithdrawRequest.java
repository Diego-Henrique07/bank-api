package com.diego.bank_api.dto.transaction;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawRequest(

        @NotNull
        Long sourceAccountId,

        @NotNull
        BigDecimal amount,

        @NotNull
        String description
) {
}
