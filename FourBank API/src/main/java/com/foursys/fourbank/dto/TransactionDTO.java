package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.enums.TransactionDirection;
import com.foursys.fourbank.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private Long id;
    private LocalDateTime transactionDate;
    private LocalDateTime appointmentDate;
    private Double value;
    private TransactionType transactionType;
    private TransactionDirection transactionDirection;
    private AccountDTO sourceAccount;
    private AccountDTO destinationAccount;
}
