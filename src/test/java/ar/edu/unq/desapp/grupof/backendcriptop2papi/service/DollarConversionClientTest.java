package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.DollarConversionClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class DollarConversionClientTest {

    private DollarConversionClient dollarClient;

    @BeforeEach
    void setUp() {
        dollarClient = new DollarConversionClient(new RestTemplate());
    }

    @Test
    void getOfficialDollarQuotationTest() {
        Double dollarPrice = dollarClient.getOfficialDollarPrice();

        Assertions.assertThat(dollarPrice).isInstanceOf(Double.class);
    }
}
