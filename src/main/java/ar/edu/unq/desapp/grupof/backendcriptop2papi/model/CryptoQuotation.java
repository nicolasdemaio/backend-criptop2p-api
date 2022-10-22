package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.RawQuote;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.DoubleFormatter.f;

@Entity
@Data
public class CryptoQuotation {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency cryptoCurrency;
    private Double priceInDollars;
    private Double priceInPesos;
    private LocalDateTime timeStamp;

    protected CryptoQuotation() { }

    public CryptoQuotation(CryptoCurrency cryptoCurrency, Double priceInDollars, Double priceInPesos, LocalDateTime timeStamp){
        this.cryptoCurrency = cryptoCurrency;
        this.priceInDollars = f(priceInDollars);
        this.priceInPesos = f(priceInPesos);
        this.timeStamp = timeStamp;
    }

    public CryptoQuotation(RawQuote rawQuote, Double officialDollarQuotation, LocalDateTime timestamp){
        this.cryptoCurrency = CryptoCurrency.valueOf(rawQuote.getSymbol());
        this.priceInDollars = f(rawQuote.getPrice());
        this.priceInPesos = f(rawQuote.getPrice() * officialDollarQuotation);
        this.timeStamp = timestamp;
    }
}
