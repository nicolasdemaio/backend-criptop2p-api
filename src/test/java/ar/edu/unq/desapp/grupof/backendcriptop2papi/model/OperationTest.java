package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.anyInvestor;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    @Test
    @DisplayName("Constructor test")
    void constructorTest(){
        InvestmentAccount anAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount anotherAccount = new InvestmentAccount(anyInvestor());
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(anAccount);
        Operation anOperation = aMarketOrder.beginAnOperationBy(anotherAccount);

        assertThat(anOperation.getParty()).isEqualTo(anAccount);
        assertThat(anOperation.getCounterparty()).isEqualTo(anotherAccount);
        assertThat(anOperation.getSourceOfOrigin()).isEqualTo(aMarketOrder);


    }

    @Test
    @DisplayName("When an Operation is created its status is NewOperationStatus")
    void newStatusTest(){
        InvestmentAccount anAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount anotherAccount = new InvestmentAccount(anyInvestor());
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(anAccount);
        Operation anOperation = aMarketOrder.beginAnOperationBy(anotherAccount);

        assertThat(anOperation.getStatus()).isEqualTo(new NewOperationStatus());
    }

    private MarketOrder anyMarketOrderIssuedBy(InvestmentAccount investmentAccount) {
        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }
}
