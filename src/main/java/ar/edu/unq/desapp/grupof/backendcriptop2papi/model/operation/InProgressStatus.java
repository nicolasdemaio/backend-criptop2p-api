package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;

import javax.persistence.Entity;

@Entity
public class InProgressStatus extends OperationStatus {
    @Override
    public Transaction processTransactionFor(Operation anOperation, OrderType orderType, InvestmentAccount transactor) {
        validateIfTransactorIsParty(anOperation, transactor);
        Transaction transaction = new Transaction(anOperation.getParty(), orderType.secondActionOfTransaction(), "N/A");
        anOperation.changeStatusTo(OperationStatus.COMPLETED);
        return transaction;
    }

    private void validateIfTransactorIsParty(Operation anOperation, InvestmentAccount transactor) {
        if (anOperation.getCounterparty() == transactor) throw new InvalidOperationException("The counterparty cannot transact once it has already transacted");
    }
}
