package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import java.util.Map;

public class InvalidObjectException extends RuntimeException {

    private final Map<String, String> constraintViolations;

    public InvalidObjectException(Map<String, String> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
