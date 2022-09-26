package ar.edu.unq.desapp.grupof.backendcriptop2papi.config;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.EmailAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    ResponseEntity emailAlreadyInUse(EmailAlreadyInUseException exception) {
        return ApiMessage.response(HttpStatus.BAD_REQUEST, exception);
    }

}

class ApiMessage {
    public static ResponseEntity response(HttpStatus status, Throwable anException) {
        Map<String, String> response =
                Map.ofEntries(
                        Map.entry("message", anException.getMessage()),
                        Map.entry("timestamp", LocalDateTime.now().toString()),
                        Map.entry("status", status.toString())
                );
        return ResponseEntity.status(status).body(response);
    }
}