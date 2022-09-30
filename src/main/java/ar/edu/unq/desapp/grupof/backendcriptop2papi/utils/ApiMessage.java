package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiMessage {

    public ResponseEntity<Map<String, Object>> response(HttpStatus status, Object aMessage) {
        Map<String, Object> response =
                Map.ofEntries(
                        Map.entry("message", aMessage),
                        Map.entry("timestamp", LocalDateTime.now().toString()),
                        Map.entry("status", status.toString())
                );
        return ResponseEntity.status(status).body(response);
    }
}