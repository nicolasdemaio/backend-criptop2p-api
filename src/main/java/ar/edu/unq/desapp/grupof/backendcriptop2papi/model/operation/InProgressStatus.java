package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

import javax.persistence.Entity;

@Entity
public class InProgressStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType) {
        Transaction transaction = new Transaction(anOperation.getParty(), orderType.secondActionOfTransaction(), "N/A");
        anOperation.changeStatusTo(OperationStatus.COMPLETED);
        return transaction;
    }
}
