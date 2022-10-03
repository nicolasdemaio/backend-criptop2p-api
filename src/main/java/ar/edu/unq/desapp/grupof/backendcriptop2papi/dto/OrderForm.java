package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.OrderTypeProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderForm {
    @JsonProperty("crypto_currency")
    private CryptoCurrency cryptoCurrency;
    @JsonProperty("nominal_quantity")
    private Double nominalQuantity;
    @JsonProperty("desired_price")
    private Double desiredPrice;
    @JsonProperty("operation_type")
    private String operationType;

    public OrderForm(CryptoCurrency cryptoCurrency, Double nominalQuantity, Double desiredPrice, String operationType) {
        this.cryptoCurrency = cryptoCurrency;
        this.nominalQuantity = nominalQuantity;
        this.desiredPrice = desiredPrice;
        this.operationType = operationType;
    }

    public MarketOrder createMarketOrder(InvestmentAccount investMentAccount, Double actualPrice) {
        OrderType orderType = OrderTypeProvider.getOrderTypeConsideringADescription(operationType);

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
