package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private Long id;
    private String fullName;
    private Double nominalQuantity;
    private Double operationAmount;
    private CryptoCurrency cryptoCurrency;
    private Integer Reputation;
    private String destinationAddress;
    private Integer numberOfOperations;
    private String action;

    public static TransactionDTO fromModel(Transaction transaction){
        return new TransactionDTO(
                transaction.getId(),
                transaction.getPartyAccount().getInvestor().getFullName(),
                transaction.getNominalQuantity(),
                transaction.getOperationAmount(),
                transaction.getCryptoQuotation().getCryptoCurrency(),
                transaction.getPartyAccount().getReputation(),
                transaction.getDestinationAddress(),
                transaction.getPartyAccount().amountOfCompletedOperations(),
                transaction.getAction()
        );
    }
}
