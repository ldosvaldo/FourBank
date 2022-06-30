package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_credit_card_bills")
@SequenceGenerator(name="tb_credit_card_bills", sequenceName = "tb_sq_credit_card_bills", allocationSize = 1, initialValue = 1)
public class CreditCardBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credit_card_bill", nullable = false)
    private Long id;
    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "is_overdue", nullable = false)
    private Boolean isOverdue;

    @ManyToOne
    @JoinColumn(name = "id_credit_card", nullable = false, referencedColumnName = "id")
    private CreditCard creditCard;

    @OneToMany(mappedBy = "creditCardBill")
    private List<Purchase> purchases;
}