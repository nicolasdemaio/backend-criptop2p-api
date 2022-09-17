package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private final MarketOrder sourceOfOrigin;
    @ManyToOne
    private final InvestmentAccount party;
    @ManyToOne
    private final InvestmentAccount counterparty;

    private OperationStatus status;

    // probablbmenete tenga un state de operation

    public Operation(MarketOrder anOrder, InvestmentAccount aParty, InvestmentAccount aCounterParty) {
        sourceOfOrigin = anOrder;
        party = aParty;
        counterparty = aCounterParty;
        status = new NewOperationStatus();
    }
}
