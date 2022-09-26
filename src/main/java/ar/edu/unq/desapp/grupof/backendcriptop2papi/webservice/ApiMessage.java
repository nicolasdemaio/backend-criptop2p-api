package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiMessage {
    public ResponseEntity<Map<String, String>> response(HttpStatus status, String aMessage) {
        Map<String, String> response =
                Map.ofEntries(
                        Map.entry("message", aMessage),
                        Map.entry("timestamp", LocalDateTime.now().toString()),
                        Map.entry("status", status.toString())
                );
        return ResponseEntity.status(status).body(response);
    }
}