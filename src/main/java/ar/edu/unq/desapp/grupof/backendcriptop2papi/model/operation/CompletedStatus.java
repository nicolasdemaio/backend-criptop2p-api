package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;

import javax.persistence.Entity;

@Entity
public class CompletedStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor) {
        throw new InvalidOperationException("The operation cannot be transacted because its status is COMPLETED");
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
