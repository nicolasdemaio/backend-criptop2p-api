package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.CryptoQuotationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

// TODO: test
@Service
public class QuotationService {

    private CryptoQuoteAPIClient cryptoQuoteAPIClient;
    private ModelMapper modelMapper;

    @Autowired
    public QuotationService(CryptoQuoteAPIClient cryptoQuoteAPIClient, ModelMapper modelMapper) {
        this.cryptoQuoteAPIClient = cryptoQuoteAPIClient;
        this.modelMapper = modelMapper;
    }

    /*
     * Nota
     * Pensé hacerlo asi pero puede cambiarse:
     * le pido las RawQuotes (nombre y precio) al Client (le pega directo a la API)
     *
     * mapeo esas RawQuote a CryptoQuotation
     * Necesita, a parte del nombre y precio (en dolares):
     * Precio en pesos, dia y fecha de actualizacion.
     * Para lo que es precio en pesos, necesitamos pegarle a la API del BRCA para saber
     * la cotizacion del dolar y hacer la conversion.
     *
     * Capaz, un CurrencyClient que le pegue a la api que tiene que ver con monedas.
     * Nos traemos la cotizacion y a un CryptoQuotationConverter le pasamos:
     * la lista de RawQuotes y la cotizacion del dolar actual.
     *
     * Por ultimo: las mapeo a CryptoQuotationDTO para retornar
     */
    public List<CryptoQuotationDTO> getAllCryptoQuotations() {
        List<RawQuote> rawQuotes = cryptoQuoteAPIClient.getCryptoQuotations();
        List<CryptoQuotation> quotations = null;
        Type listType = new TypeToken<List<CryptoQuotationDTO>>() {}.getType();
        return modelMapper.map(quotations, listType);
    }
}
