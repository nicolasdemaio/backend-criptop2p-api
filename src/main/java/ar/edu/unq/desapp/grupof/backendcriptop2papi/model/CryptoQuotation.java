package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class CryptoQuotation {

    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency cryptoCurrency;
    private Double priceInDollars;
    private Double priceInPesos;
    private LocalDateTime timeStamp;

    protected CryptoQuotation() { }

    public CryptoQuotation(CryptoCurrency cryptoCurrency, Double priceInDollars, Double priceInPesos, LocalDateTime timeStamp){
        this.cryptoCurrency = cryptoCurrency;
        this.priceInDollars = priceInDollars;
        this.priceInPesos = priceInPesos;
        this.timeStamp = timeStamp;
    }
}
