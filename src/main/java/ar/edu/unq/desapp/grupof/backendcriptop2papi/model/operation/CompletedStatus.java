package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OperationNotCancellableException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

import javax.persistence.Entity;

@Entity
public class CompletedStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType) {
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
