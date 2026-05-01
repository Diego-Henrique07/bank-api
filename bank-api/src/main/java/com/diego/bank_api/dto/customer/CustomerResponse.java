package com.diego.bank_api.dto.customer;

import com.diego.bank_api.entity.enums.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String fullName,
        String email,
        String documentNumber,
        String phone,
        LocalDate birthDate,
        CustomerStatus status,
        LocalDateTime createdAt
){}

