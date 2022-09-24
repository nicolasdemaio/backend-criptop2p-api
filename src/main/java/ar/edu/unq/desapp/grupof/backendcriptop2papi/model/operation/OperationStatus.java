package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OperationStatus {

    public final static OperationStatus COMPLETED = new CompletedStatus();
    public final static OperationStatus IN_PROGRESS = new InProgressStatus();
    public final static OperationStatus NEW = new NewOperationStatus();
    public final static OperationStatus CANCELLED = new CancelledStatus();

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    public abstract Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor);

    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        Transaction transaction = new Transaction(anAccount, "Cancel", "N/A");
        anOperation.changeStatusTo(OperationStatus.CANCELLED);
        return transaction;
    }

    public boolean isCompleted(){
        return false;
    }

}
