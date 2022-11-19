package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.CryptoQuoteAPIClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.client.DollarConversionClient;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationRecordRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationsCache;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.QuotationRecord;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuotationService {

    private CryptoQuoteAPIClient cryptoQuoteAPIClient;

    private DollarConversionClient dollarClient;

    private QuotationRecordRepository quotationRecordRepository;

    private QuotationsCache quotationsCache;

    @Autowired
    public QuotationService(CryptoQuoteAPIClient cryptoQuoteAPIClient, DollarConversionClient dollarClient,
                            QuotationRecordRepository quotationRecordRepository, QuotationsCache quotationsCache) {
        this.cryptoQuoteAPIClient = cryptoQuoteAPIClient;
        this.dollarClient = dollarClient;
        this.quotationRecordRepository = quotationRecordRepository;
        this.quotationsCache = quotationsCache;
    }

    public List<QuotationRecord> getLast24HourQuotations(CryptoCurrency cryptoCurrency) {
        return quotationRecordRepository.getQuotationsWithTimeStampBefore(
                cryptoCurrency,
                LocalDateTime.now().minusHours(24L)
        );
    }

    /**
     * Fetch cached crypto quotations.
     * In case of empty results, fetch quotations from API Client.
     * return list of Crypto Quotations
     **/
    public List<CryptoQuotation> getAllCryptoQuotations() {
        List<CryptoQuotation> cryptoQuotations = quotationsCache.getAll();
        if (cryptoQuotations.isEmpty()) {
            List<RawQuote> rawQuotes = cryptoQuoteAPIClient.getCryptoQuotations();

            cryptoQuotations = convertRawQuotesIntoQuotations(rawQuotes);
        }
        return cryptoQuotations;
    }

    /**
     * Fetch crypto quotations from API Client >> Binance
     * return list of Crypto Quotations
     **/
    public List<CryptoQuotation> getOnlineCryptoQuotations() {
        List<RawQuote> rawQuotes = cryptoQuoteAPIClient.getCryptoQuotations();
        return convertRawQuotesIntoQuotations(rawQuotes);
    }

    public CryptoQuotation getCryptoQuotation(CryptoCurrency currency){
        CryptoQuotation quotation;
        try {
            quotation = quotationsCache.get(currency);
        } catch (FetchNotFoundException exception) {
            RawQuote rawQuote = this.cryptoQuoteAPIClient.searchQuoteByCryptoCurrency(currency);
            Double officialDollarPrice = this.dollarClient.getOfficialDollarPrice();
            quotation = new CryptoQuotation(rawQuote, officialDollarPrice, LocalDateTime.now());
        }
        return quotation;
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
