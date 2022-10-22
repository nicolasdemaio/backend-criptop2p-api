package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetStatistic {
    private CryptoCurrency cryptoCurrency;
    private Double nominalQuantity;
    private Double currentQuotationInDollars;
    private Double currentQuotationInPesos;

    public void addNominalQuantity(Double amount) {
        this.nominalQuantity += amount;
    }
}
