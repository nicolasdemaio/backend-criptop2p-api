package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

public class PurchaseOrder extends OrderType {
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

}
