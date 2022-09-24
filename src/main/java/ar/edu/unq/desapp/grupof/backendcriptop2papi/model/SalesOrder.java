package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

public class SalesOrder extends OrderType {
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
}
