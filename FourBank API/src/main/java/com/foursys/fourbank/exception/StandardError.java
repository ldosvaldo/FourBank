package com.foursys.fourbank.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
