package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String password;
    private AddressDTO address;
}