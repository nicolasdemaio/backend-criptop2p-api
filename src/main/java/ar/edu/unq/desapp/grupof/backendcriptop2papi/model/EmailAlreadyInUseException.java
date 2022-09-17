package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
