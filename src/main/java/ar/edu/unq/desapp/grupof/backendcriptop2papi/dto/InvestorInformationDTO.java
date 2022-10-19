package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InvestorInformationDTO {

    private String name;
    private String surname;
    @JsonProperty("number_of_operations")
    private Integer numberOfOperations;
    private Integer reputation;

    public static InvestorInformationDTO fromModel(InvestmentAccount account) {
        return new InvestorInformationDTO(account);
    }

    public InvestorInformationDTO(InvestmentAccount account) {
        name = account.getInvestor().getName();
        surname = account.getInvestor().getSurname();
        numberOfOperations = account.getOperations().size();
        reputation = account.getReputation();
    }
}
