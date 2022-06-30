package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_purchases")
@SequenceGenerator(name="tb_purchases", sequenceName = "tb_sq_purchases", allocationSize = 1, initialValue = 1)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchase", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String store;

    @ManyToOne
    @JoinColumn(name = "id_credit_card_bill", nullable = false, referencedColumnName = "id_credit_card_bill")
    private CreditCardBill creditCardBill;
}
