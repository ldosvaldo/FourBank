package com.foursys.fourbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_adresses")
@SequenceGenerator(name="tb_adresses", sequenceName = "tb_sq_adresses", allocationSize = 1, initialValue = 1)
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_address", nullable = false)
	private Long id;

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(nullable = false)
	private Integer number;

	@Column(name = "public_place", nullable = false)
	private String publicPlace;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String country;

	private String complement;

	@OneToOne(mappedBy = "address")
	@JsonIgnore
	private User user;
}