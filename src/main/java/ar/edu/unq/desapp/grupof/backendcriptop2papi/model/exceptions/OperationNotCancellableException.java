package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;


public class OperationNotCancellableException extends RuntimeException {
    public OperationNotCancellableException() {
        super("Operation can not be cancelled because it is not active");
    }
}
