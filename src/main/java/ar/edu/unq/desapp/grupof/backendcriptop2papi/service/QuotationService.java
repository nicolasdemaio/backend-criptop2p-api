package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.CryptoQuoteAPIClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.DollarConversionClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.RawQuote;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuotationService {

    private CryptoQuoteAPIClient cryptoQuoteAPIClient;

    private DollarConversionClient dollarClient;

    @Autowired
    public QuotationService(CryptoQuoteAPIClient cryptoQuoteAPIClient, DollarConversionClient dollarClient) {
        this.cryptoQuoteAPIClient = cryptoQuoteAPIClient;
        this.dollarClient = dollarClient;
    }

    @Transactional
    public List<CryptoQuotation> getAllCryptoQuotations() {
        List<RawQuote> rawQuotes = cryptoQuoteAPIClient.getCryptoQuotations();

        return convertRawQuotesIntoQuotations(rawQuotes);
    }

    @Transactional
    public CryptoQuotation getCryptoQuotation(CryptoCurrency currency){
        RawQuote rawQuote = this.cryptoQuoteAPIClient.searchQuoteByCryptoCurrency(currency);
        Double officialDollarPrice = this.dollarClient.getOfficialDollarPrice();
        return new CryptoQuotation(rawQuote, officialDollarPrice, LocalDateTime.now());
    }

    private List<CryptoQuotation> convertRawQuotesIntoQuotations(List<RawQuote> rawQuotes){
        Double officialDollarQuotation = dollarClient.getOfficialDollarPrice();

        return rawQuotes.stream()
                .map(quote -> new CryptoQuotation(quote,
                officialDollarQuotation,
                LocalDateTime.now()
        ))
                .toList();
    }


}
