package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

public class InvestorNotFoundException extends RuntimeException{
    public InvestorNotFoundException() {
        super("Investor not found");
    }
}
