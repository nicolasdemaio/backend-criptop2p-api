package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("Email is already in use");
    }
}
