package ar.edu.unq.desapp.grupof.backendcriptop2papi.client;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCryptoCurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DollarConversionClient {
    private static final String URL = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";

    private RestTemplate restTemplate;

    @Autowired
    public DollarConversionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getOfficialDollarPrice() {
        List<RawDollarQuote> rawQuotations = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RawDollarQuote>>() {
                }
        ).getBody();
        return obtainOfficialPrice(rawQuotations);
    }

    public Double obtainOfficialPrice(List<RawDollarQuote> quotes) {
        var officialDollarQuote =
                quotes
                        .stream()
                        .filter(quote -> quote.hasAsDescription("Dolar Oficial"))
                        .findFirst()
                        .orElseThrow(InvalidCryptoCurrencyException::new);

        return officialDollarQuote.sellingPrice();
    }



}
