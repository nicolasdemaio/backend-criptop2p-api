package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.RawQuote;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class CryptoQuotation {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
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

    public CryptoQuotation(RawQuote rawQuote, Double officialDollarQuotation, LocalDateTime timestamp){
        this.cryptoCurrency = CryptoCurrency.valueOf(rawQuote.getSymbol());
        this.priceInDollars = rawQuote.getPrice();
        this.priceInPesos = rawQuote.getPrice() * officialDollarQuotation;
        this.timeStamp = timestamp;
    }
}
