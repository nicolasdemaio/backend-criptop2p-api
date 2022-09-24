package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

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

    protected Transaction() { }

    public Transaction(InvestmentAccount anAccount, String action) {
        this.partyAccount = anAccount;
        this.action = action;
    }
}
