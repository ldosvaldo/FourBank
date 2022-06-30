package com.foursys.fourbank.dto;

import lombok.Data;

@Data
public class PixChargeFORM {

    private Double value;
    private OtherAccountDTO destinationAccount = new OtherAccountDTO();

}
