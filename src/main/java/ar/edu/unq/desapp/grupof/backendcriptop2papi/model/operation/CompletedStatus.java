package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class CompletedStatus extends OperationStatus {

    public CompletedStatus() {
        status = "COMPLETED";
    }

    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor, LocalDateTime transactionDateTime) {
        throw new InvalidOperationException("The operation cannot be transacted because its status is COMPLETED");
    }

    @Override
    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        throw new OperationNotCancellableException();
    }

    @Override
    public boolean isCompleted(){
        return true;
    }

    @Override
    public void cancelBySystem(Operation anOperation) {
        throw new OperationNotCancellableException();
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
