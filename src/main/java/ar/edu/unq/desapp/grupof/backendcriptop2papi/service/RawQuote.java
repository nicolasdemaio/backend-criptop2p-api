package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.DoubleFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RawQuote {

    @Getter
    private String symbol;
    private Double price;

    public Double getPrice() {
        return DoubleFormatter.f(price);
    }

}
