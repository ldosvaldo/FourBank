package com.foursys.fourbank.dto;

import java.time.LocalDate;
import com.foursys.fourbank.model.CreditCard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InsurancePolicyDTO {
	private Long id;
    private String number;
    private LocalDate hiringDate;
    private Double value;
    private String roles;
}
