package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.enums.PixType;
import com.foursys.fourbank.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixDTO {

	private Long id;
    private String pixKeyValue;
    private PixType pixType;
}
