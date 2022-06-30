package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.enums.PixType;
import com.foursys.fourbank.model.Pix;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixKeysDTO {

	private Long id;
    private PixType pixType;
    private String pixKeyValue;
}
