package com.diego.bank_api.entity;
import com.diego.bank_api.entity.enums.CardStatus;
import com.diego.bank_api.entity.enums.CardType;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@RequiredArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String cardNumber;

    @Getter
    @Setter
    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @Getter
    @Setter
    private LocalDate expirationDate;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;
}
