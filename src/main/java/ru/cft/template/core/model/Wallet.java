package ru.cft.template.core.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "balance", nullable = false)
    private int balance;
}
