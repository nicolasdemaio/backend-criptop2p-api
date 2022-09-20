package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class CompletedStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation) {
        // Debe romper?
        return null;
    }
}
