package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.DoubleFormatter.f;

@Entity
@Data
public class QuotationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency cryptoCurrency;
    private Double priceInDollars;
    private Double priceInPesos;
    private LocalDateTime timeStamp;

    protected QuotationRecord() {
    }

    public QuotationRecord(CryptoQuotation cryptoQuotation) {
        this.cryptoCurrency = cryptoQuotation.getCryptoCurrency();
        this.priceInDollars = cryptoQuotation.getPriceInDollars();
        this.priceInPesos = cryptoQuotation.getPriceInPesos();
        this.timeStamp = cryptoQuotation.getTimeStamp();
    }


}