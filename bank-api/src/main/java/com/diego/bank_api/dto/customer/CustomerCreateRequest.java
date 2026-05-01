package com.diego.bank_api.dto.customer;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CustomerCreateRequest(

        @NotBlank(message = "Name must not be empty")
        String fullName,

        @NotBlank(message = "Email must not be empty")
        String email,

        @NotBlank(message = "Document number must not be empty")
        String documentNumber,

        @NotBlank
        String phone,

        LocalDate birthDate
){}
