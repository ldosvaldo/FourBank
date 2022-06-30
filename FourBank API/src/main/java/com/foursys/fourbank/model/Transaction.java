package com.foursys.fourbank.model;

import com.foursys.fourbank.enums.TransactionDirection;
import com.foursys.fourbank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_transactions")
@SequenceGenerator(name="tb_transactions", sequenceName = "tb_sq_transactions", allocationSize = 1, initialValue = 1)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction", nullable = false)
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_direction", nullable = false)
    private TransactionDirection transactionDirection;

    @ManyToOne
    @JoinColumn(name = "id_source_account", nullable = false)
    private FourBankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "id_destination_account", nullable = false)
    private Account destinationAccount;
}