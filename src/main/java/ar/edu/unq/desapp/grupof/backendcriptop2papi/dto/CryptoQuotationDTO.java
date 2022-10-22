package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoQuotationDTO {
    @JsonProperty("crypto_currency")
    private CryptoCurrency cryptoCurrency;
    @JsonProperty("price_in_dollars")
    private Double priceInDollars;
    @JsonProperty("price_in_pesos")
    private Double priceInPesos;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonProperty("time_stamp")
    private LocalDateTime timeStamp;
}
