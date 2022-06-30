package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPasswordDTO {
	    private String newPassword;
}
