package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OperationStatus {

    public static final OperationStatus COMPLETED = new CompletedStatus();
    public static final OperationStatus IN_PROGRESS = new InProgressStatus();
    public static final OperationStatus NEW = new NewOperationStatus();
    public static final OperationStatus CANCELLED = new CancelledStatus();

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    public abstract Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor, LocalDateTime transactionDateTime);

    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        Transaction transaction = new Transaction(anAccount, "Cancel", "N/A", anOperation.getCryptoQuotation(), LocalDateTime.now());
        anOperation.changeStatusTo(OperationStatus.CANCELLED);
        return transaction;
    }

    public boolean isCompleted(){
        return false;
    }

    public void cancelBySystem(Operation anOperation) {
        anOperation.changeStatusTo(OperationStatus.CANCELLED);
    }

    public boolean isActive() {
        return true;
    }
}
