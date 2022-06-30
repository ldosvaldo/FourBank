package com.foursys.fourbank.model;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	private Long id;
	@Column(nullable = false)
	private String agency;
	@Column(nullable = false, unique = true)
	private String number;
	@Column(nullable = false, name = "bank_number")
	private String bankNumber;

	@OneToMany(mappedBy = "account")
	private List<Pix> pixList;

	public abstract String getCpf();

	public abstract String getUserName();
}
