package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.CryptoQuotationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.QuotationRecord;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(path = "/api/quotations")
public class QuotationController {

    private QuotationService quotationService;
    private ModelMapper modelMapper;

    @Autowired
    public QuotationController(QuotationService quotationService, ModelMapper modelMapper) {
        this.quotationService = quotationService;
        this.modelMapper = modelMapper;
    }

    @Operation (summary = "Get a List of Quotations for every Cryptocurrency")
    @GetMapping
    public ResponseEntity<List<CryptoQuotation>> getCryptoQuotations() {
        List<CryptoQuotation> quotations = quotationService.getAllCryptoQuotations();
        Type listType = new TypeToken<List<CryptoQuotationDTO>>() {}.getType();
        return ResponseEntity.ok(modelMapper.map(quotations, listType));
    }

    @Operation (summary = "Get a quotations list of last 24 hours for a crypto currency")
    @GetMapping("/{cryptoCurrency}/records")
    public ResponseEntity<List<QuotationRecord>> getLast24HourQuotations(@PathVariable CryptoCurrency cryptoCurrency) {
        List<QuotationRecord> quotations = quotationService.getLast24HourQuotations(cryptoCurrency);
        return ResponseEntity.ok(quotations);
    }

}
