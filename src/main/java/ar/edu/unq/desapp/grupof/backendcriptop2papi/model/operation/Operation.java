package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCancellationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private MarketOrder sourceOfOrigin;
    @ManyToOne
    private InvestmentAccount party;
    @ManyToOne
    private InvestmentAccount counterparty;

    @OneToMany
    private List<Transaction> transactions;

    @ManyToOne
    private OperationStatus status;

    protected Operation() { }

    public Operation(MarketOrder anOrder, InvestmentAccount aParty, InvestmentAccount aCounterParty) {
        sourceOfOrigin = anOrder;
        party = aParty;
        counterparty = aCounterParty;
        status = OperationStatus.NEW;
        transactions = new ArrayList();
    }

    public Transaction transact(InvestmentAccount transactor, CryptoQuotation quotation) {
        if (isThirdParty(transactor)) throw new InvalidOperationException("The operation cannot be transacted by a third party");
        Transaction processedTransaction = status.processTransactionFor(this, sourceOfOrigin.getOrderType(), transactor);
        transactions.add(processedTransaction);
        return processedTransaction;
    }

    public Transaction cancelBy(InvestmentAccount account){
        if (isThirdParty(account)) throw new InvalidCancellationException();
        Transaction processedTransaction = status.cancel(this, account); //cambiar estado a cancel
        transactions.add(processedTransaction);
        account.discountPointsForCancellation();
        return processedTransaction;
    }

    // Default access modifier - It can be invoked only by class and another classes on same package.
    void changeStatusTo(OperationStatus aStatus) {
        status = aStatus;
    }

    public boolean isCompleted(){
        return status.isCompleted();
    }

    private boolean isThirdParty(InvestmentAccount account){
        return (account != this.party) && (account != this.counterparty);
    }
}
