package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.model.CreditCardBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDTO {

    private Long id;
    private Double value;
    private LocalDate date;
    private String store;
}