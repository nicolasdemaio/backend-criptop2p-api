package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
public class CryptoQuotation {
    private String name;
    private Double priceInDollars;
    private Double priceInPesos;
    private LocalDateTime timeStamp;

    public CryptoQuotation(String name, Double priceInDollars, Double priceInPesos, LocalDateTime timeStamp){
        this.name = name;
        this.priceInDollars = priceInDollars;
        this.priceInPesos = priceInPesos;
        this.timeStamp = timeStamp;
    }
}
