package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() {
        super("Order not found");
    }
}
