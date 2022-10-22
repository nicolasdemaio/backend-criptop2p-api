package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.CryptoQuotationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<List<CryptoQuotation>> getCryptoQuotations() {
        List<CryptoQuotation> quotations = quotationService.getAllCryptoQuotations();
        Type listType = new TypeToken<List<CryptoQuotationDTO>>() {}.getType();
        return ResponseEntity.ok(modelMapper.map(quotations, listType));
    }
}
