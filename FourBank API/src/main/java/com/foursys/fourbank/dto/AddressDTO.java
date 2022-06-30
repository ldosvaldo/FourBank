package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private Long id;
    private String zipCode;
    private Integer number;
    private String publicPlace;
    private String city;
    private String state;
    private String country;
    private String complement;

}
