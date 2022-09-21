package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class InvalidCancellationException extends RuntimeException {
    public InvalidCancellationException() {
        super("Operation can not be cancelled by a third party account");
    }
}
