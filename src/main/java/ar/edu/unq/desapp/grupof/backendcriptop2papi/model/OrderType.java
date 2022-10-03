package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderTypeException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SalesOrder.class, name = "sales order"),
        @JsonSubTypes.Type(value = PurchaseOrder.class, name = "purchase order")
})
public abstract class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public abstract String firstActionOfTransaction();
    public abstract String secondActionOfTransaction();

    public abstract String destinationAddressFrom(InvestmentAccount anAccount);

    public abstract boolean isSuitablePrice(CryptoQuotation currentQuotation, Double desiredPrice);
}
