package com.foursys.fourbank.model;

import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FourBankAccount extends Account{

	@Column(nullable = false)
	private Double balance;

	@OneToOne
	@JoinColumn(name = "id_notification", referencedColumnName = "id_notification")
	private Notification notification;

	@OneToMany(mappedBy = "sourceAccount")
	private List<Transaction> transactions;

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id_user")
	private User user;

	@Override
	public String getUserName() {
		return user.getName();
	}

	@Override
	public String getCpf() {
		return user.getCpf();
	}
}