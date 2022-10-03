package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class InvalidOrderTypeException extends RuntimeException {
    public InvalidOrderTypeException() {
        super("Invalid operation type, it must be SALES or PURCHASE");
    }
}
