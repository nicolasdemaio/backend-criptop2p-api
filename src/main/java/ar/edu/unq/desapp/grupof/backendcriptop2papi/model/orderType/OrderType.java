package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public abstract class OrderType {

    @Id
    protected String type;

    public abstract String firstActionOfTransaction();
    public abstract String secondActionOfTransaction();

    public abstract String destinationAddressFrom(InvestmentAccount anAccount);

    public abstract boolean isSuitablePrice(CryptoQuotation currentQuotation, Double desiredPrice);
}
