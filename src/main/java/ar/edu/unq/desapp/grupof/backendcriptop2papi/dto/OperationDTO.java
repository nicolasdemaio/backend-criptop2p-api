package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationDTO {

    private Long id;
    @JsonProperty("crypto_quotation")
    private CryptoQuotationDTO cryptoQuotation;
    @JsonProperty("date_time")
    private LocalDateTime dateTimeOfOrigin;
    @JsonProperty("nominal_quantity")
    private Double nominalQuantity;
    private String party;
    private String counterparty;
    private String status;

    public static OperationDTO fromModel(Operation operation) {
        return new OperationDTO(operation);
    }

    private OperationDTO(Operation operation) {
        id = operation.getId();
        cryptoQuotation = cryptoQuotation(operation);
        dateTimeOfOrigin = operation.getDateTimeOfOrigin();
        nominalQuantity = operation.getSourceOfOrigin().getNominalQuantity();
        party = investorFrom(operation.getParty());
        counterparty = investorFrom(operation.getCounterparty());
        status = statusFrom(operation);
    }

    private String investorFrom(InvestmentAccount investmentAccount) {
        Investor investor = investmentAccount.getInvestor();
        String investorName = investor.getName();
        String investorSurname = investor.getSurname();
        return investorName + " " + investorSurname;
    }

    private String statusFrom(Operation operation) {
        return operation.getStatus().toString();
    }

    private CryptoQuotationDTO cryptoQuotation(Operation operation) {
        CryptoQuotation cryptoQuotation = operation.getCryptoQuotation();
        return new CryptoQuotationDTO(
          cryptoQuotation.getCryptoCurrency(),
          cryptoQuotation.getPriceInDollars(),
          cryptoQuotation.getPriceInPesos(),
          cryptoQuotation.getTimeStamp()
        );
    }

}
