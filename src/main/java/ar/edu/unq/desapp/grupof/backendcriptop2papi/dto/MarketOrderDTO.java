package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

// TODO: revisar si va la reputacion del chabon y mas info.
// capaz un investor dto que tenga eso ya, reputacion, operaciones y su info

@Data
@AllArgsConstructor
public class MarketOrderDTO {

    private Long id;
    @JsonProperty("crypto_currency")
    private CryptoCurrency cryptoCurrency;
    @JsonProperty("quantity")
    private Double nominalQuantity;
    @JsonProperty("price")
    private Double desiredPrice;
    @JsonProperty("order_type")
    private OrderType orderType;
    private Double actualPrice;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
}
