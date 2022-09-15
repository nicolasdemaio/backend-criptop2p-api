package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(marketOrder.getEmitter()).isEqualTo(investmentAccount);
        assertThat(marketOrder.getDesiredPrice()).isEqualTo(desiredPrice);
        assertThat(marketOrder.getOrderType()).isEqualTo(orderType);
        assertThat(marketOrder.getActualPrice()).isEqualTo(actualPrice);
        assertThat(marketOrder.getDateTime()).isEqualTo(aDateTime);
    }

    @Test
    @DisplayName("A market order cannot be created with desired price above 5% of the actual price")
    void testCannotBeCreatedWithPriceAboveFivePercentOfActualPrice() {
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        Double desiredPrice = 25d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        assertThatThrownBy(
                () -> new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime))
                .isInstanceOf(InvalidOrderPriceException.class)
                .hasMessage("Desired price should be in the range of +/- 5% from current price");
    }

    @Test
    @DisplayName("A market order cannot be created with desired price below 5% of the actual price")
    void testCannotBeCreatedWithPriceBelowFivePercentOfActualPrice() {
        Investor anInvestor = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");
        InvestmentAccount investmentAccount = new InvestmentAccount(anInvestor);

        Double desiredPrice = 10d;
        Double actualPrice = 15d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        assertThatThrownBy(
                () -> new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime))
                .isInstanceOf(InvalidOrderPriceException.class)
                .hasMessage("Desired price should be in the range of +/- 5% from current price");
    }


}
