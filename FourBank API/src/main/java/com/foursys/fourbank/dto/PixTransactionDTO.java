package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixTransactionDTO {
    private Long id;
    private Double value;
    private LocalDateTime transactionDate;
    private Long idSourceAccount;
    private PixKeysDTO pixKeys;
}
