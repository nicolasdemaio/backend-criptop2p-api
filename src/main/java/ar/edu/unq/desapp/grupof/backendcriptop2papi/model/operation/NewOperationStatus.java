package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

import javax.persistence.Entity;

@Entity
public class NewOperationStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType) {
        Transaction transaction =
                new Transaction(anOperation.getCounterparty(), orderType.firstActionOfTransaction(), orderType.destinationAddressFrom(anOperation.getCounterparty()));
        anOperation.changeStatusTo(OperationStatus.IN_PROGRESS);
        return transaction;
    }
}
