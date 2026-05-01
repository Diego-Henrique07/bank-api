package com.diego.bank_api.entity;
import com.diego.bank_api.entity.enums.CustomerStatus;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String fullName;

    @Getter
    @Setter
    @Column(nullable = false,unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String documentNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private String phone;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDate birthDate;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> account = new ArrayList<>();
}
