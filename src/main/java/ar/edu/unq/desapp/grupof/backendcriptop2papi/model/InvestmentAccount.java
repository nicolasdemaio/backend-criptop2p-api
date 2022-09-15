package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InvestmentAccount implements Account {


    private final List<MarketOrder> marketOrders;
    private final Investor investor;
    private final List<Operation> operations;

    public InvestmentAccount(Investor anInvestor) {
        marketOrders = new ArrayList<MarketOrder>();
        investor = anInvestor;
        operations = new ArrayList<Operation>();
    }

    public void placeMarketOrder(MarketOrder aMarketOrder) {
        marketOrders.add(aMarketOrder);
    }

    public void applyFor(MarketOrder aMarketOrder) {
        aMarketOrder.isTakenBy(this);
    }

    public void addOperation(Operation anOperation) {
        operations.add(anOperation);
    }
}
