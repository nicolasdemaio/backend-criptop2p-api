package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class InProgressStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation) {
        Transaction transaction = new Transaction(anOperation.getParty());
        anOperation.changeStatusTo(OperationStatus.COMPLETED);
        return transaction;
    }
}
