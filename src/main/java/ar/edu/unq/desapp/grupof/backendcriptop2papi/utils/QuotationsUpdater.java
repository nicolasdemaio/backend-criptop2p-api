package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationRecordRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.QuotationsCache;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class QuotationsUpdater {

    private QuotationService quotationService;

    private QuotationsCache quotationsCache;

    private QuotationRecordRepository quotationRecordRepository;

    @Autowired
    public QuotationsUpdater(QuotationService quotationService , QuotationsCache quotationsCache, QuotationRecordRepository quotationRecordRepository) {
        this.quotationService = quotationService;
        this.quotationsCache = quotationsCache;
        this.quotationRecordRepository = quotationRecordRepository;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void updateQuotationsOnCache() {
        List<CryptoQuotation> quotations = quotationService.getOnlineCryptoQuotations();
        quotations.forEach(quotation -> {
            quotationsCache.put(quotation.getCryptoCurrency(),quotation);
            quotationRecordRepository.save(new QuotationRecord(quotation));
        });
    }

}
