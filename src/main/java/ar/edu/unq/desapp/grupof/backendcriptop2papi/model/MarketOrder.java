package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MarketOrder {

    private final String cryptoCurrency;
    private final InvestmentAccount investmentAccount;
    private final Double nominalQuantity;
    private final Double desiredPrice;
    private final OrderType orderType;
    private final Double actualPrice;
    private final LocalDateTime dateTime;

    // actualPrice probablemente no se guarda.
    public MarketOrder(String cryptoCurrency, InvestmentAccount investmentAccount, Double nominalQuantity, Double desiredPrice, OrderType orderType, Double actualPrice, LocalDateTime dateTime) {
        this.cryptoCurrency = cryptoCurrency;
        this.investmentAccount = investmentAccount;
        this.nominalQuantity = nominalQuantity;
        this.desiredPrice = desiredPrice;
        this.orderType = orderType;
        this.actualPrice = actualPrice;
        this.dateTime = dateTime;
    }
}


