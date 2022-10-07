package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OperationDTO {

    private Long id;

    private CryptoQuotation cryptoQuotation;

    private LocalDateTime dateTimeOfOrigin;

    private Double nominalQuantity;

    public static OperationDTO fromModel(Operation operation) {
        return
                new OperationDTO(
                        operation.getId(),
                        operation.getCryptoQuotation(),
                        operation.getDateTimeOfOrigin(),
                        operation.getSourceOfOrigin().getNominalQuantity());
    }

}
