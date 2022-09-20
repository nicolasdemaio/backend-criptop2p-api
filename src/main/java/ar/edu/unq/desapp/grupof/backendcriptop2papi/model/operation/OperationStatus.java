package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;

public abstract class OperationStatus {

    public final static OperationStatus COMPLETED = new CompletedStatus();
    public final static OperationStatus IN_PROGRESS = new InProgressStatus();
    public final static OperationStatus NEW = new NewOperationStatus();

    public abstract Transaction processTransactionFor(Operation anOperation);
}
