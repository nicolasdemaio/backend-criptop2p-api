package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.CryptoCurrencyProvider;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.OrderTypeProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderForm {
    @JsonProperty("crypto_currency")
    private String cryptoCurrency;
    @JsonProperty("nominal_quantity")
    @Positive (message = "Nominal quantity must be higher than zero.")
    private Double nominalQuantity;
    @JsonProperty("desired_price")
    private Double desiredPrice;
    @JsonProperty("operation_type")
    private String operationType;

    public OrderForm(String cryptoCurrency, Double nominalQuantity, Double desiredPrice, String operationType) {
        this.cryptoCurrency = cryptoCurrency;
        this.nominalQuantity = nominalQuantity;
        this.desiredPrice = desiredPrice;
        this.operationType = operationType;
    }

    public MarketOrder createMarketOrder(InvestmentAccount investMentAccount, Double actualPrice) {
        OrderType orderType = OrderTypeProvider.getOrderTypeConsideringADescription(operationType);
        CryptoCurrency cryptoAsset = getCryptoCurrency();

        return new MarketOrder(
                cryptoAsset,
                investMentAccount,
                this.nominalQuantity,
                this.desiredPrice,
                orderType,
                actualPrice,
                LocalDateTime.now()
        );
    }

    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrencyProvider.getCryptoCurrencyFor(cryptoCurrency);
    }
}
