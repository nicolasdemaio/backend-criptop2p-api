package ar.edu.unq.desapp.grupof.backendcriptop2papi.config;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.ApiMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    ResponseEntity<Map<String, Object>> emailAlreadyInUse(EmailAlreadyInUseException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Map<String, Object>> onConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> error = new HashMap<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler(InvestorNotFoundException.class)
    ResponseEntity<Map<String, Object>> investorNotFound(InvestorNotFoundException exception) {
        return new ApiMessage().response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InvalidOrderTypeException.class)
    ResponseEntity<Map<String, Object>> invalidOrderType(InvalidOrderTypeException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidCryptoCurrencyException.class)
    ResponseEntity<Map<String, Object>> invalidCryptoCurrency(InvalidCryptoCurrencyException exception){
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Map<String, Object>> parsingException(HttpMessageNotReadableException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidOrderPriceException.class)
    ResponseEntity<Map<String, Object>> invalidOrderPriceException(InvalidOrderPriceException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(OrderAlreadyTakenException.class)
    ResponseEntity<Map<String, Object>> orderAlreadyTakenException(OrderAlreadyTakenException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<Map<String, Object>> orderNotFoundException(OrderNotFoundException exception) {
        return new ApiMessage().response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

}


