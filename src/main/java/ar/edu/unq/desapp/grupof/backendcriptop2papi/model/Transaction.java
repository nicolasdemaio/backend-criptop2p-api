package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

// Puede ser como un registro, transaccion de quien a quien. de cuanto, en que fecha ...
@Getter
public class Transaction {

    private final Account partyAccount;
    private final String action;

    public Transaction(InvestmentAccount anAccount, String action) {
        this.partyAccount = anAccount;
        this.action = action;
    }
}
