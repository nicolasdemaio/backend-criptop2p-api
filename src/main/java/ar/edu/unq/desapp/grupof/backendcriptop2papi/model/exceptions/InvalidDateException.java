package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException() {
        super("The second date can not be before the first one");
    }
}