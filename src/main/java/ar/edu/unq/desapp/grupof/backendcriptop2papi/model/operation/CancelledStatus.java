package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;

import javax.persistence.Entity;

@Entity
public class CancelledStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor) {
        throw new InvalidOperationException("The operation cannot be transacted because its status is CANCELLED");
    }

    @Override
    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        throw new OperationNotCancellableException();
    }
}
