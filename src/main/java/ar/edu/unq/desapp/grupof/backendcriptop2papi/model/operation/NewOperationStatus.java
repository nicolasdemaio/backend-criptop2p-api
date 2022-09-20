package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class NewOperationStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation) {
        Transaction transaction = new Transaction(anOperation.getCounterparty());
        anOperation.changeStatusTo(OperationStatus.IN_PROGRESS);
        return transaction;
    }
}
