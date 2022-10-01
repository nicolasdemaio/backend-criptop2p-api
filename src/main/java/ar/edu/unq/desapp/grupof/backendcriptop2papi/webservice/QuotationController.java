package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.CryptoQuotationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: TEST
@RestController
@RequestMapping(path = "/api/quotation")
public class QuotationController {

    private QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @GetMapping
    public ResponseEntity<List<CryptoQuotationDTO>> getCryptoQuotations() {
        return ResponseEntity.ok(quotationService.getAllCryptoQuotations());
    }
}
