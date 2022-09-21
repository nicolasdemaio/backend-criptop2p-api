package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class CompletedStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation) {
        // Debe romper?
        return null;
    }

    @Override
    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        // Debe romper?
        throw new OperationNotCancellableException();
    }

    @Override
    public boolean isCompleted(){
        return true;
    }
}
