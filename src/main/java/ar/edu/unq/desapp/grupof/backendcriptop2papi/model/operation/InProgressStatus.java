package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.PointsIncrementer;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class InProgressStatus extends OperationStatus {

    public InProgressStatus() {
        status = "IN_PROGRESS";
    }

    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor, LocalDateTime transactionDateTime) {
        validateIfTransactorIsParty(anOperation, transactor);
        Transaction transaction = new Transaction(anOperation.getParty(), orderType.secondActionOfTransaction(), "N/A", anOperation.getCryptoQuotation(), transactionDateTime, anOperation.getSourceOfOrigin());
        anOperation.changeStatusTo(OperationStatus.completed());
        increasePointsToAccountsOf(anOperation, transactionDateTime);
        return transaction;
    }

    private void increasePointsToAccountsOf(Operation anOperation, LocalDateTime transactionDateTime) {
        new PointsIncrementer(anOperation).incrementPointsConsidering(transactionDateTime);
    }

    private void validateIfTransactorIsParty(Operation anOperation, InvestmentAccount transactor) {
        if (anOperation.getCounterparty() == transactor) throw new InvalidOperationException("The counterparty cannot transact once it has already transacted");
    }
}
