package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
/**
 * Consumer of external API to fetch crypto quotations.
 * It returns 'raw quote' - object according to the response of API.
 * You should map your objects from this object.
 */
public class CryptoQuoteAPIClient {

    private static final String URL = "https://api1.binance.com/api/v3/ticker/price";

    private RestTemplate restTemplate;

    @Autowired
    public CryptoQuoteAPIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RawQuote searchQuoteByCryptoCurrency(CryptoCurrency aCryptoCurrency) {
        String url = URL.concat("?symbol=").concat(aCryptoCurrency.name());
        return restTemplate.getForObject(url, RawQuote.class);
    }

    public List<RawQuote> getCryptoQuotations() {
        String url = URL.concat("?symbols=").concat(currenciesSymbols());
        return
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<RawQuote>>() {
                        }
                ).getBody();
    }

    private String currenciesSymbols() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        CryptoCurrency.symbols().stream().forEach(symbol -> stringBuilder.append("\"" + symbol + "\""));
        stringBuilder.append(']');
        return stringBuilder.toString().replaceAll("\"" + "\"", "\",\"");
    }

}
