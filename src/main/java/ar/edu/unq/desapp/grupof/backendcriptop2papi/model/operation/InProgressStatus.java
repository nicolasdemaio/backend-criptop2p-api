package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public class InProgressStatus extends OperationStatus {
    @Override //cambiar el not applies
    public Transaction processTransactionFor(Operation anOperation) {
        Transaction transaction = new Transaction(anOperation.getParty(), "Not applies");
        anOperation.changeStatusTo(OperationStatus.COMPLETED);
        return transaction;
    }
}
