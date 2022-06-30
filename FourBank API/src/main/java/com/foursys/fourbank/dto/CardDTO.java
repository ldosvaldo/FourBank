package com.foursys.fourbank.dto;

import com.foursys.fourbank.enums.CardFlag;
import com.foursys.fourbank.enums.CreditCardType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
	private Long id;
    private String userName;
    private String number;
    private LocalDate expirationDate;
    private String securityCode;
    private Boolean isBlocked = true;
    private String password;
    private Double maxLimit;
    private Double currentLimit;
    private Double availableLimit;
    private Double usageFee ;
    private Double dailyLimit ;
    private CreditCardType creditCardType;
    private CardFlag flag;
}