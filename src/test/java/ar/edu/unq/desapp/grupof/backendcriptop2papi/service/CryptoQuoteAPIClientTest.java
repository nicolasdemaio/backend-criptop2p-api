package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.CryptoQuoteAPIClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CryptoQuoteAPIClientTest {

    private CryptoQuoteAPIClient cryptoQuoteAPIClient;

    @BeforeEach
    void setUp() {
        cryptoQuoteAPIClient = new CryptoQuoteAPIClient(new RestTemplate());
    }

    @Test
    @DisplayName("When search quote by symbol, it returns the current quote")
    void testGetQuoteBySymbol(){
        RawQuote foundQuote = cryptoQuoteAPIClient.searchQuoteByCryptoCurrency(CryptoCurrency.BNBUSDT);

        assertThat(foundQuote.getSymbol()).isEqualTo(CryptoCurrency.BNBUSDT.name());
        assertThat(foundQuote.getPrice()).isNotNull();
    }

    @Test
    @DisplayName("Can fetch all crypto quotations")
    void testGetAllCryptoQuotations() {
        List<RawQuote> foundQuote = cryptoQuoteAPIClient.getCryptoQuotations();

        List<String> expectedResult = CryptoCurrency.symbols();

        List<String> foundQuotes = foundQuote.stream().map(RawQuote::getSymbol).toList();

        assertThat(foundQuotes).containsAll(expectedResult);
    }

}
