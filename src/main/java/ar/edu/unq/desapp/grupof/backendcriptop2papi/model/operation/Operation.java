package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCancellationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private MarketOrder sourceOfOrigin;
    @ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private InvestmentAccount party;
    @ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private InvestmentAccount counterparty;

    @OneToMany (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private CryptoQuotation cryptoQuotation;

    @ManyToOne
    private OperationStatus status;

    private LocalDateTime dateTimeOfOrigin;

    protected Operation() { }

    public Operation(MarketOrder anOrder, InvestmentAccount aParty, InvestmentAccount aCounterParty, CryptoQuotation aCryptoQuotation) {
        sourceOfOrigin = anOrder;
        party = aParty;
        counterparty = aCounterParty;
        status = OperationStatus.NEW;
        transactions = new ArrayList<>();
        cryptoQuotation = aCryptoQuotation;
        dateTimeOfOrigin = LocalDateTime.now();
    }

    public Transaction transact(InvestmentAccount transactor, LocalDateTime transactionDateTime) {
        if (isThirdParty(transactor)) throw new InvalidOperationException("The operation cannot be transacted by a third party");
        Transaction processedTransaction = status.processTransactionFor(this, sourceOfOrigin.getOrderType(), transactor, transactionDateTime);
        transactions.add(processedTransaction);
        return processedTransaction;
    }

    public Transaction cancelBy(InvestmentAccount account){
        if (isThirdParty(account)) throw new InvalidCancellationException();
        Transaction processedTransaction = status.cancel(this, account);
        transactions.add(processedTransaction);
        account.discountPointsForCancellation();
        return processedTransaction;
    }

    public void changeStatusTo(OperationStatus aStatus) {
        status = aStatus;
    }

    public boolean isCompleted(){
        return status.isCompleted();
    }

    private boolean isThirdParty(InvestmentAccount account){
        return (account != this.party) && (account != this.counterparty);
    }

    public void systemCancel() {
        status.cancelBySystem(this);
    }

    public boolean isActive() {
        return status.isActive();
    }
}
