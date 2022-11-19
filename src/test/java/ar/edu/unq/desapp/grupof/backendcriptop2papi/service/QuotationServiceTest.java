package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.CryptoQuoteAPIClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.DollarConversionClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationRecordRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationsCache;
import org.assertj.core.api.Assertions;
import org.hibernate.FetchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

class QuotationServiceTest {

    private CryptoQuoteAPIClient cryptoQuoteClient = Mockito.mock(CryptoQuoteAPIClient.class);
    private DollarConversionClient dollarClient = Mockito.mock(DollarConversionClient.class);
    private QuotationsCache quotationsCache = Mockito.mock(QuotationsCache.class);
    private QuotationRecordRepository quotationRecordRepository = Mockito.mock(QuotationRecordRepository.class);

    private QuotationService quotationService;

    @BeforeEach
    void setUp() {
        this.quotationService = new QuotationService(cryptoQuoteClient, dollarClient, quotationRecordRepository, quotationsCache);
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
    void getCryptoQuotationTestWhenQuotationIsNotCached() {
        String cryptoName = CryptoCurrency.BNBUSDT.name();
        Double priceInDollars = 20d;
        Double dollarPrice = 134.25;
        Mockito.when(cryptoQuoteClient.searchQuoteByCryptoCurrency(CryptoCurrency.BNBUSDT)).thenReturn(new RawQuote(cryptoName,priceInDollars));
        Mockito.when(dollarClient.getOfficialDollarPrice()).thenReturn(dollarPrice);
        Mockito.when(quotationsCache.get(CryptoCurrency.BNBUSDT)).thenThrow(FetchNotFoundException.class);

        CryptoQuotation quotation = quotationService.getCryptoQuotation(CryptoCurrency.BNBUSDT);

        Assertions.assertThat(quotation.getCryptoCurrency().name()).isEqualTo(cryptoName);
        Assertions.assertThat(quotation.getPriceInDollars()).isEqualTo(priceInDollars);
        Assertions.assertThat(quotation.getPriceInPesos()).isEqualTo(priceInDollars * dollarPrice);
    }

    @Test
    void getCryptoQuotationTestWhenQuotationWasCached() {
        CryptoCurrency crypto = CryptoCurrency.BNBUSDT;
        Double priceInDollars = 20d;
        Mockito.when(quotationsCache.get(crypto))
                .thenReturn(
                        new CryptoQuotation(crypto, priceInDollars, priceInDollars, LocalDateTime.now()));

        CryptoQuotation quotation = quotationService.getCryptoQuotation(crypto);

        org.junit.jupiter.api.Assertions.assertNotNull(quotation);
        Mockito.verify(quotationsCache, Mockito.atMostOnce()).get(crypto);
    }
}
