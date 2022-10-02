package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class CryptoQuotationDTO {
    private CryptoCurrency cryptoCurrency;
    private Double priceInDollars;
    private Double priceInPesos;
    private LocalDateTime timeStamp;
}
