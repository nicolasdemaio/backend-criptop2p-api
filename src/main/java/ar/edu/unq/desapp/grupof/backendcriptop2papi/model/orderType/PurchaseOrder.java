package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;

import javax.persistence.Entity;

@Entity
public class PurchaseOrder extends OrderType {

    public PurchaseOrder() {
        type = "PURCHASE";
    }

    @Override
    public String firstActionOfTransaction() {
        return "Crypto asset has been sent.";
    }

    @Override
    public String secondActionOfTransaction() {
        return "Money has been sent.";
    }

    @Override
    public String destinationAddressFrom(InvestmentAccount anAccount) {
        return anAccount.getInvestor().getMercadoPagoCVU();
    }

    @Override
    public boolean isSuitablePrice(CryptoQuotation currentQuotation, Double desiredPrice) {
        return currentQuotation.getPriceInPesos() <= desiredPrice;
    }

}
