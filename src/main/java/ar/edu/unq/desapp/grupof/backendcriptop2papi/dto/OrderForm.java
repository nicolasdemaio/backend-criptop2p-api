package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.CryptoCurrencyFactory;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.OrderTypeFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.DoubleFormatter.f;

@Builder
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
        OrderType orderType = OrderTypeFactory.getOrderTypeAccordingTo(operationType);
        CryptoCurrency cryptoAsset = getCryptoCurrency();

        return new MarketOrder(
                cryptoAsset,
                investMentAccount,
                this.nominalQuantity,
                f(this.desiredPrice),
                orderType,
                f(actualPrice),
                LocalDateTime.now()
        );
    }

    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrencyFactory.getCryptoCurrencyFor(cryptoCurrency);
    }
}
