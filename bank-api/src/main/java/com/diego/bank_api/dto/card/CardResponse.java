package com.diego.bank_api.dto.card;
import com.diego.bank_api.entity.enums.CardStatus;
import com.diego.bank_api.entity.enums.CardType;

import java.time.*;

public record CardResponse(
        Long id,
        String cardNumber,
        String holderName,
        LocalDate expirationDate,
        CardType cardType,
        CardStatus status,
        LocalDateTime createdAt,
        Long accountId
) {}
