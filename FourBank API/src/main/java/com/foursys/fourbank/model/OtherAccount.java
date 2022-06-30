package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_other_accounts")
@SequenceGenerator(name="tb_other_accounts", sequenceName = "tb_sq_other_accounts", allocationSize = 1, initialValue = 1)
public class OtherAccount extends Account{

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String cpf;
}
