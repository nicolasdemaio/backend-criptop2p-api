package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

public class Operation {

    private final MarketOrder sourceOfOrigin;
    private final InvestmentAccount party;
    private final InvestmentAccount counterparty;

    // probablbmenete tenga un state de operation

    public Operation(MarketOrder anOrder, InvestmentAccount aParty, InvestmentAccount aCounterParty) {
        sourceOfOrigin = anOrder;
        party = aParty;
        counterparty = aCounterParty;
    }
}
