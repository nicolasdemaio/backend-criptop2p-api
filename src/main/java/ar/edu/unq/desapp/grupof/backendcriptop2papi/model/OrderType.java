package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.CryptoQuotation;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public abstract class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public abstract String firstActionOfTransaction();
    public abstract String secondActionOfTransaction();

    public abstract String destinationAddressFrom(InvestmentAccount anAccount);

    public abstract boolean isSuitablePrice(CryptoQuotation currentQuotation, Double desiredPrice);
}
