package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import java.util.HashMap;

public class InvalidObjectException extends RuntimeException {

    private final HashMap<String, String> constraintViolations;

    public InvalidObjectException(HashMap<String, String> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
