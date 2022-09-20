package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.anyInvestor;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    private InvestmentAccount partyAccount;
    private InvestmentAccount counterPartyAccount;

    @BeforeEach
    void initialize() {
        partyAccount = new InvestmentAccount(anyInvestor());
        counterPartyAccount = new InvestmentAccount(anyInvestor());
    }

    @Test
    @DisplayName("Constructor test")
    void constructorTest(){
        MarketOrder marketOrder = anyMarketOrderIssuedBy(partyAccount);
        Operation anOperation = marketOrder.beginAnOperationBy(counterPartyAccount);

        assertThat(anOperation.getParty()).isEqualTo(partyAccount);
        assertThat(anOperation.getCounterparty()).isEqualTo(counterPartyAccount);
        assertThat(anOperation.getSourceOfOrigin()).isEqualTo(marketOrder);
        assertThat(anOperation.getTransactions()).isEmpty();
    }

    @Test
    @DisplayName("When an Operation is created its status is NewOperationStatus")
    void newStatusTest(){
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        Operation anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount);

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.NEW);
    }

    private MarketOrder anyMarketOrderIssuedBy(InvestmentAccount investmentAccount) {
        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }

    @Test
    @DisplayName("When an operation is transacted by counter party, its status is changed to InProgressStatus")
    void testFirstTransaction() {
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        Operation anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount);

        Transaction generatedTransaction = anOperation.transact();

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.IN_PROGRESS);
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(counterPartyAccount);
    }

    @Test
    @DisplayName("When an operation is transacted by party, its status is completed")
    void testSecondTransaction() {
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        Operation anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount);

        anOperation.transact();

        Transaction generatedTransaction = anOperation.transact();

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.COMPLETED);
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(partyAccount);
    }
}
