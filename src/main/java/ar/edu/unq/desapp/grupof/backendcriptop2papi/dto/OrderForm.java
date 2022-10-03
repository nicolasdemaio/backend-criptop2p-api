package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderTypeException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderForm {
    private CryptoCurrency cryptoCurrency;
    private Double nominalQuantity;
    private Double desiredPrice;
    private String operationType; //Ver si usar un builder.

    private Map<String, OrderType> orderTypes;

    public OrderForm(CryptoCurrency cryptoCurrency, Double nominalQuantity, Double desiredPrice, String operationType) {
        this.cryptoCurrency = cryptoCurrency;
        this.nominalQuantity = nominalQuantity;
        this.desiredPrice = desiredPrice;
        this.operationType = operationType;
        this.orderTypes = Map.ofEntries(Map.entry("SALES", new SalesOrder()), Map.entry("PURCHASE", new PurchaseOrder()));
    }

    public MarketOrder createMarketOrder(InvestmentAccount investMentAccount, Double actualPrice){
        OrderType orderType = this.orderTypes.get(this.operationType);
        if (orderType == null) throw new InvalidOrderTypeException();

        return new MarketOrder(
                this.cryptoCurrency,
                investMentAccount,
                this.nominalQuantity,
                this.desiredPrice,
                orderType,
                actualPrice,
                LocalDateTime.now()
        );
    }
}
