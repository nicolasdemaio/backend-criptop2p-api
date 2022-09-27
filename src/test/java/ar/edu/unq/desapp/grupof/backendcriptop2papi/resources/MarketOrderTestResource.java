package ar.edu.unq.desapp.grupof.backendcriptop2papi.resources;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;

import java.time.LocalDateTime;

public class MarketOrderTestResource {

    public static MarketOrder anyMarketOrderIssuedBy(InvestmentAccount investmentAccount) {
        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        OrderType orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder(CryptoCurrency.ATOMUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }

    public static MarketOrder anySalesMarketOrderIssuedByWithDesiredPrice(InvestmentAccount investmentAccount, Double desiredPrice) {
        Double actualPrice = desiredPrice;
        OrderType orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder(CryptoCurrency.BTCUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }

    public static MarketOrder anyPurchaseMarketOrderIssuedByWithDesiredPrice(InvestmentAccount investmentAccount, Double desiredPrice) {
        Double actualPrice = desiredPrice;
        OrderType orderType = new PurchaseOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder(CryptoCurrency.ADAUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }
}
