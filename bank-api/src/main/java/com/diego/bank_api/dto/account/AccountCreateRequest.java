package com.diego.bank_api.dto.account;

import com.diego.bank_api.entity.enums.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountCreateRequest(
        @NotNull
        Long customerId,

        @NotNull
        AccountType accountType
){





}
