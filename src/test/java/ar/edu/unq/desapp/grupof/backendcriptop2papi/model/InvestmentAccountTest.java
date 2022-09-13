package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class InvestmentAccountTest {

    @Test
    @DisplayName("When account is created, it belongs to an investor")
    void testAccountCreationWithInvestor() {
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "123331233123", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        assertThat(investmentAccount.getInvestor()).isEqualTo(anInvestor);
    }

    @Test
    @DisplayName("When account is created, its order list is empty")
    void testAccountDoesNotContainOrdersWhenIsCreated() {
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        assertThat(investmentAccount.getMarketOrders().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("An account can register a market order")
    void testAddOrder(){
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        MarketOrder aMarketOrder = new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);


        investmentAccount.addMarketOrder(aMarketOrder);

        assertThat(investmentAccount.getMarketOrders().contains(aMarketOrder)).isTrue();
    }

}
