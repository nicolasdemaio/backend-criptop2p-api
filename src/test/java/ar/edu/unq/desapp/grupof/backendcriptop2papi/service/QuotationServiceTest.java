package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.CryptoQuoteAPIClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.DollarConversionClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.RawQuote;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class QuotationServiceTest {

    private CryptoQuoteAPIClient cryptoQuoteClient = Mockito.mock(CryptoQuoteAPIClient.class);
    private DollarConversionClient dollarClient = Mockito.mock(DollarConversionClient.class);

    private QuotationService quotationService;

    @BeforeEach
    void setUp() {
        this.quotationService = new QuotationService(cryptoQuoteClient, dollarClient);
    }

    @Test
    void getAllCryptoQuotationsTest() {
        String cryptoName = CryptoCurrency.BNBUSDT.name();
        Double priceInDollars = 20d;
        Double dollarPrice = 134.25;
        Mockito.when(cryptoQuoteClient.getCryptoQuotations()).thenReturn(List.of(new RawQuote(cryptoName,priceInDollars)));
        Mockito.when(dollarClient.getOfficialDollarPrice()).thenReturn(dollarPrice);

        List<CryptoQuotation> quotations = quotationService.getAllCryptoQuotations();
        CryptoQuotation quotation = quotations.get(0);

        Assertions.assertThat(quotations).hasSize(1);
        Assertions.assertThat(quotation.getCryptoCurrency().name()).isEqualTo(cryptoName);
        Assertions.assertThat(quotation.getPriceInDollars()).isEqualTo(priceInDollars);
        Assertions.assertThat(quotation.getPriceInPesos()).isEqualTo(priceInDollars * dollarPrice);

    }

    @Test
    void getAllCryptoQuotationsWithTwoQuotationsTest() {
        String cryptoName = CryptoCurrency.BNBUSDT.name();
        Double priceInDollars = 20d;
        Double dollarPrice = 134.25;
        RawQuote rawQuote = new RawQuote(cryptoName,priceInDollars);
        Mockito.when(cryptoQuoteClient.getCryptoQuotations()).thenReturn(List.of(rawQuote,rawQuote));
        Mockito.when(dollarClient.getOfficialDollarPrice()).thenReturn(dollarPrice);

        List<CryptoQuotation> quotations = quotationService.getAllCryptoQuotations();

        Assertions.assertThat(quotations).hasSize(2);
    }

    @Test
    void getCryptoQuotationTest() {
        String cryptoName = CryptoCurrency.BNBUSDT.name();
        Double priceInDollars = 20d;
        Double dollarPrice = 134.25;
        Mockito.when(cryptoQuoteClient.searchQuoteByCryptoCurrency(CryptoCurrency.BNBUSDT)).thenReturn(new RawQuote(cryptoName,priceInDollars));
        Mockito.when(dollarClient.getOfficialDollarPrice()).thenReturn(dollarPrice);

        CryptoQuotation quotation = quotationService.getCryptoQuotation(CryptoCurrency.BNBUSDT);

        Assertions.assertThat(quotation.getCryptoCurrency().name()).isEqualTo(cryptoName);
        Assertions.assertThat(quotation.getPriceInDollars()).isEqualTo(priceInDollars);
        Assertions.assertThat(quotation.getPriceInPesos()).isEqualTo(priceInDollars * dollarPrice);

    }
}
