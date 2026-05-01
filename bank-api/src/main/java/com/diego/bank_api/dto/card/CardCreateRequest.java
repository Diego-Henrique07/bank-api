package com.diego.bank_api.dto.card;

import com.diego.bank_api.entity.enums.CardType;

public record CardCreateRequest(

        Long accountId,
        CardType cardType
) {
}
