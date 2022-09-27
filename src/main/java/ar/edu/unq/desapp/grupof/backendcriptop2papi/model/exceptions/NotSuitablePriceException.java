package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class NotSuitablePriceException extends RuntimeException {
    public NotSuitablePriceException(String message) {
        super(message);
    }
}