package com.diego.bank_api.entity;
import com.diego.bank_api.entity.enums.AccountStatus;
import com.diego.bank_api.entity.enums.AccountType;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private String agencyNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal balance;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
