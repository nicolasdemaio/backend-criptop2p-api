package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

// Puede ser como un registro, transaccion de quien a quien. de cuanto, en que fecha ...
@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private InvestmentAccount partyAccount;
    private String action;

    @ManyToOne
    private CryptoQuotation cryptoQuotation;

    private LocalDateTime timeStamp;
    private String destinationAddress;

    private Double nominalQuantity;

    private Double operationAmount;

    protected Transaction() { }

    public Transaction(InvestmentAccount anAccount, String action, String destinationAddress, CryptoQuotation aCryptoQuotation, LocalDateTime transactionDateTime, MarketOrder marketOrder) {
        this.partyAccount = anAccount;
        this.action = action;
        this.destinationAddress = destinationAddress;
        this.cryptoQuotation = aCryptoQuotation;
        this.timeStamp = transactionDateTime;
        this.nominalQuantity = marketOrder.getNominalQuantity();
        this.operationAmount = marketOrder.getTotalAmount();
    }
}
