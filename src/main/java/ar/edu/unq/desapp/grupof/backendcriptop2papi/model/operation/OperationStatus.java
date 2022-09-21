package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public abstract class OperationStatus {

    public final static OperationStatus COMPLETED = new CompletedStatus();
    public final static OperationStatus IN_PROGRESS = new InProgressStatus();
    public final static OperationStatus NEW = new NewOperationStatus();
    public final static OperationStatus CANCELLED = new CancelledStatus();

    public abstract Transaction processTransactionFor(Operation anOperation);

    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        Transaction transaction = new Transaction(anAccount, "Cancel");
        anOperation.changeStatusTo(OperationStatus.CANCELLED);
        return transaction;
    }
    public boolean isCompleted(){
        return false;
    }
}
