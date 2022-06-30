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
@Table(name = "tb_transactional_accounts")
@SequenceGenerator(name="tb_transactional_accounts", sequenceName = "tb_sq_transactional_accounts", allocationSize = 1, initialValue = 1)
public class TransactionalAccount extends FourBankAccount{
	
	@Column(name = "balance_limit", nullable = false)
	private Double balanceLimit;
	@Column(name = "maintenance_fee", nullable = false)
	private Double maintenanceFee;
}