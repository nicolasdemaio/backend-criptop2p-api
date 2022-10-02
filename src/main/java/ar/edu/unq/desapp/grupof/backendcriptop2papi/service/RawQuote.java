package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RawQuote {

    private String symbol;
    private Double price;

}
