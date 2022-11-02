package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class QuotationsUpdater {

    private QuotationService quotationService;

    // private QuotationsCache quotationsCache;

    @Autowired
    public QuotationsUpdater(QuotationService quotationService /*,QuotationsCache quotationsCache*/) {
        this.quotationService = quotationService;
        // this.quotationsCache = quotationsCache;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void updateQuotationsOnCache() {
        // print para ver que funciona -> loggear con lo4j para ir trackeando
        System.out.println("10sec");
        var quotations = quotationService.getAllCryptoQuotations();
        // for each quotation -> save into cache
    }

}
