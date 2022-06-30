package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.model.CreditCardBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardBillDTO {

    private Long id;
    private Double value;
    private LocalDate dueDate;
    private Boolean isPaid;
    private Boolean isOverdue;
    private List<PurchaseDTO> purchase;

}
