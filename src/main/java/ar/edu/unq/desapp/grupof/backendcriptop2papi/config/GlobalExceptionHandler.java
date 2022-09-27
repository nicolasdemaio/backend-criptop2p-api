package ar.edu.unq.desapp.grupof.backendcriptop2papi.config;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.EmailAlreadyInUseException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice.ApiMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    ResponseEntity<Map<String, String>> emailAlreadyInUse(EmailAlreadyInUseException exception) {
        return new ApiMessage().response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}

