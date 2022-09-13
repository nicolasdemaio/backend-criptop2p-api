package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketOrderTest {

    @Test
    @DisplayName("Market order is created correctly")
    void testCreateMarketOrder(){
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        MarketOrder marketOrder = new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);

        assertThat(marketOrder.getCryptoCurrency()).isEqualTo("BNBUSDT");
        assertThat(marketOrder.getNominalQuantity()).isEqualTo(0.1d);
        assertThat(marketOrder.getInvestmentAccount()).isEqualTo(investmentAccount);
        assertThat(marketOrder.getDesiredPrice()).isEqualTo(desiredPrice);
        assertThat(marketOrder.getOrderType()).isEqualTo(orderType);
        assertThat(marketOrder.getActualPrice()).isEqualTo(actualPrice);
        assertThat(marketOrder.getDateTime()).isEqualTo(aDateTime);
    }

}
