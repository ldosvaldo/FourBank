package com.foursys.fourbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_saving_accounts")
@SequenceGenerator(name="tb_savings_accounts", sequenceName = "tb_sq_savings_accounts", allocationSize = 1, initialValue = 1)
public class SavingsAccount extends FourBankAccount{
	
	@Column(nullable = false)
	private Double yieldRate;
}
