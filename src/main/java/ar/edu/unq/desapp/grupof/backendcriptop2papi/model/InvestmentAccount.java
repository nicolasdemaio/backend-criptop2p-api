package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InvestmentAccount implements Account {


    private final List<MarketOrder> marketOrders;
    private final Investor investor;

    public InvestmentAccount(Investor anInvestor) {
        marketOrders = new ArrayList();
        investor = anInvestor;
    }

    public void addMarketOrder(MarketOrder aMarketOrder) {
        marketOrders.add(aMarketOrder);
    }
}
