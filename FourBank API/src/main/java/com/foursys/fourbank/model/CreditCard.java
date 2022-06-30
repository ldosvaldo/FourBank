package com.foursys.fourbank.model;

import com.foursys.fourbank.enums.CreditCardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_credit_cards")
@SequenceGenerator(name="tb_credit_cards", sequenceName = "tb_sq_credit_cards", allocationSize = 1, initialValue = 1)
public class CreditCard extends Card{
    @Column(name = "max_limit", nullable = false)
    private Double maxLimit;

    @Column(name = "current_limit", nullable = false)
    private Double currentLimit;

    @Column(name = "available_limit", nullable = false)
    private Double availableLimit;

    @Column(name = "usage_fee", nullable = false)
    private Double usageFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_card_type", nullable = false)
    private CreditCardType creditCardType;

    @OneToMany(mappedBy = "creditCard")
    private List<CreditCardBill> bills;

    @OneToMany(mappedBy = "creditCard")
    private List<InsurancePolicy> insurancePolicies;
}