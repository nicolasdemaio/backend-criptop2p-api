package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketOrderDTO {

    private Long id;
    @JsonProperty("crypto_currency")
    private CryptoCurrency cryptoCurrency;
    @JsonProperty("quantity")
    private Double nominalQuantity;
    @JsonProperty("price")
    private Double desiredPrice;
    @JsonProperty("order_type")
    private String orderType;
    @JsonProperty("total_amount")
    private Double totalAmount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    @JsonProperty("user")
    private String userInfo;
    @JsonProperty("operations_quantity")
    private Integer operationsQuantity;
    private Integer reputation;

    public static MarketOrderDTO fromModel(MarketOrder anOrder) {

        InvestmentAccount emitter = anOrder.getEmitter();

        return
                new MarketOrderDTO(
                        anOrder.getId(),
                        anOrder.getCryptoCurrency(),
                        anOrder.getNominalQuantity(),
                        anOrder.getDesiredPrice(),
                        anOrder.getOrderType().getType(),
                        anOrder.getTotalAmount(),
                        anOrder.getDateTime(),
                        userInfoFrom(emitter),
                        emitter.amountOfCompletedOperations(),
                        emitter.getReputation());
    }

    /**
     * @param emitter - investment account
     * @return fullname of investment account's owner
     */
    private static String userInfoFrom(InvestmentAccount emitter) {
        Investor investor = emitter.getInvestor();
        return investor.getFullName();
    }
}
