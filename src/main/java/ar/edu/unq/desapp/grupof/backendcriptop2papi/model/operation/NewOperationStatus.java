package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class NewOperationStatus extends OperationStatus {

    public NewOperationStatus() {
        status = "NEW";
    }

    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor, LocalDateTime transactionDateTime) {
        validateIfTransactorIsCounterparty(anOperation, transactor);
        Transaction transaction =
                new Transaction(anOperation.getCounterparty(), orderType.firstActionOfTransaction(), orderType.destinationAddressFrom(anOperation.getCounterparty()), anOperation.getCryptoQuotation(), transactionDateTime, anOperation.getSourceOfOrigin());
        anOperation.changeStatusTo(OperationStatus.inProgress());
        return transaction;
    }

    private void validateIfTransactorIsCounterparty(Operation anOperation, InvestmentAccount transactor) {
        if (anOperation.getParty() == transactor) throw new InvalidOperationException("The party cannot transact until the counter party transacts");
    }
}
