package ar.edu.unq.desapp.grupof.backendcriptop2papi.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RawDollarQuote {
    private Casa casa;

    public boolean hasAsDescription(String aDescription) {
        return getCasa().getNombre().equals(aDescription);
    }

    public Double sellingPrice() {
        return getCasa().getSellingPrice();
    }
}
