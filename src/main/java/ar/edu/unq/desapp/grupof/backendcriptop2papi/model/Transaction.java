package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

// Puede ser como un registro, transaccion de quien a quien. de cuanto, en que fecha ...
@Getter
public class Transaction {

    private final Account partyAccount;

    public Transaction(InvestmentAccount anAccount) {
        partyAccount = anAccount;
    }
}
