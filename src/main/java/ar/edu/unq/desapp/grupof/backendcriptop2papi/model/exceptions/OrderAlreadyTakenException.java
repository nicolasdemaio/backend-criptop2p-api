package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class OrderAlreadyTakenException extends RuntimeException {
    public OrderAlreadyTakenException() {
        super("This order is already taken");
    }
}
