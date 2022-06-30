package com.foursys.fourbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundHandlerMethod(EntityNotFoundException ex, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 404, "Not found", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
    }

    @ExceptionHandler(UnreportedEssentialFieldException.class)
    public ResponseEntity<StandardError> unreportedEssentialFieldHandlerMethod(UnreportedEssentialFieldException ex, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 400, "Bad Request", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<StandardError> InvalidValueHandlerMethod(InvalidValueException ex, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 400, "Bad Request", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }
    
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<StandardError> UnauthorizedOperationException(UnauthorizedOperationException ex, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 422, "Unprocessable Entity", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(se);
    }
}