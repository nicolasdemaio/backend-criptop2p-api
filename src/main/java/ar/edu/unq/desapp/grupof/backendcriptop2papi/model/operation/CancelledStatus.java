package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OperationNotCancellableException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class CancelledStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation) {
        // Debe romper?
        return null;
    }

    @Override
    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        throw new OperationNotCancellableException();
    }
}
