package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SalesOrder extends OrderType {

    @Id
    protected String type = "SALES";


    @Override
    public String firstActionOfTransaction() {
        return "Money has been sent.";
    }

    @Override
    public String secondActionOfTransaction() {
        return "Crypto asset has been sent.";
    }

    @Override
    public String destinationAddressFrom(InvestmentAccount anAccount) {
        return anAccount.getInvestor().getCryptoWalletAddress();
    }

    @Override
    public boolean isSuitablePrice(CryptoQuotation currentQuotation, Double desiredPrice) {
        return currentQuotation.getPriceInPesos() >= desiredPrice;
    }
}
