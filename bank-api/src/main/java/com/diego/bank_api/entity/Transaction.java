package com.diego.bank_api.entity;
import com.diego.bank_api.entity.enums.TransactionStatus;
import com.diego.bank_api.entity.enums.TransactionType;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    @Getter
    @Setter
    @Column(nullable = false)
    private String description;

    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;

    @Getter
    @Setter
    @Column(nullable = true)
    private BigDecimal sourceBalanceBefore;

    @Getter
    @Setter
    @Column(nullable = true)
    private BigDecimal sourceBalanceAfter;

    @Getter
    @Setter
    @Column(nullable = true)
    private BigDecimal targetBalanceBefore;

    @Getter
    @Setter
    @Column(nullable = true)
    private BigDecimal targetBalanceAfter;






}
