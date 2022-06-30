package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_insurance_policies")
@SequenceGenerator(name="tb_insurance_policies", sequenceName = "tb_sq_insurance_policies", allocationSize = 1, initialValue = 1)
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insurance_policy", nullable = false)
    private Long id;

    @Column(nullable = false, name = "policy_number")
    private String number;

    @Column(name = "hiring_date", nullable = false)
    private LocalDate hiringDate;

    @Column(nullable = false, name = "policy_value")
    private Double value;

    @Column(nullable = false, name = "policy_roles")
    private String roles;

    @ManyToOne
    @JoinColumn(name = "id_credit_card", referencedColumnName = "id", nullable = false)
    private CreditCard creditCard;
}
