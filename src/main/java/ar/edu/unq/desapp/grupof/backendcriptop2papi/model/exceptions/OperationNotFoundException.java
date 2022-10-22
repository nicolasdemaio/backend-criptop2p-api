package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class OperationNotFoundException extends RuntimeException{
    public OperationNotFoundException() {
        super("Operation not found");
    }
}
