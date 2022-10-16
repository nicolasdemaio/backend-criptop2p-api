package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
public abstract class OperationStatus {

    public static OperationStatus completed() {
        return new CompletedStatus();
    }
    public static OperationStatus inProgress() {
        return new InProgressStatus();
    }
    public static OperationStatus cancelled() {
        return new CancelledStatus();
    }
    public static OperationStatus newOperation() {
        return new NewOperationStatus();
    }

    @Id
    protected String status;

    public abstract Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor, LocalDateTime transactionDateTime);

    public Transaction cancel(Operation anOperation, InvestmentAccount anAccount) {
        Transaction transaction = new Transaction(anAccount, "Cancel", "N/A", anOperation.getCryptoQuotation(), LocalDateTime.now());
        anOperation.changeStatusTo(OperationStatus.cancelled());
        return transaction;
    }

    public boolean isCompleted(){
        return false;
    }

    public void cancelBySystem(Operation anOperation) {
        anOperation.changeStatusTo(OperationStatus.cancelled());
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationStatus that = (OperationStatus) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }
}
