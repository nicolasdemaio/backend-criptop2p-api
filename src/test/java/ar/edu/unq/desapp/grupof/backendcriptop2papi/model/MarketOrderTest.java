package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderPriceException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.NotSuitablePriceException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OrderAlreadyTakenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderTestResource.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.*;

class MarketOrderTest {



    @Test
    @DisplayName("Market order is created correctly")
    void testCreateMarketOrder(){

        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());

        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        MarketOrder marketOrder = new MarketOrder(CryptoCurrency.AAVEUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);

        assertThat(marketOrder.getCryptoCurrency()).isEqualTo(CryptoCurrency.AAVEUSDT);
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
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());

        Double desiredPrice = 25d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        assertThatThrownBy(
                () -> new MarketOrder(CryptoCurrency.ADAUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime))
                .isInstanceOf(InvalidOrderPriceException.class)
                .hasMessage("Desired price should be in the range of +/- 5% from current price");
    }

    @Test
    @DisplayName("A market order cannot be created with desired price below 5% of the actual price")
    void testCannotBeCreatedWithPriceBelowFivePercentOfActualPrice() {
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());

        Double desiredPrice = 10d;
        Double actualPrice = 15d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        assertThatThrownBy(
                () -> new MarketOrder(CryptoCurrency.BNBUSDT, investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime))
                .isInstanceOf(InvalidOrderPriceException.class)
                .hasMessage("Desired price should be in the range of +/- 5% from current price");
    }

    @Test
    @DisplayName("A market order cannot be taken twice")
    void testCannotBeTakenASecondTime() {
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount anotherInvestmentAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount yetAnotherInvestmentAccount = new InvestmentAccount(anyInvestor());

        MarketOrder marketOrder = anyMarketOrderIssuedBy(investmentAccount);
        marketOrder.beginAnOperationBy(anotherInvestmentAccount, anySuitableQuotationFor(marketOrder));
        assertThatThrownBy(
                () -> marketOrder.beginAnOperationBy(yetAnotherInvestmentAccount, anySuitableQuotationFor(marketOrder)))
                .isInstanceOf(OrderAlreadyTakenException.class)
                .hasMessage("This order is already taken");
    }

    @Test
    @DisplayName("A market order cannot be taken by its issuer")
    void testCannotBeTakenByIssuer(){
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());
        MarketOrder marketOrder = anyMarketOrderIssuedBy(investmentAccount);

        assertThatThrownBy(
                () -> marketOrder.beginAnOperationBy(investmentAccount, null))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("An order cannot be taken by its emitter");
    }

    @Test
    @DisplayName("When an account wants to begin sales operation and the current price is below the desired price a Cancelled Operation is issued for emitter and transactor")
    void testBeginSalesOperationWithNotSuitablePrice(){
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount anotherInvestmentAccount = new InvestmentAccount(anyInvestor());
        MarketOrder marketOrder = anySalesMarketOrderIssuedByWithDesiredPrice(investmentAccount,20d);

        CryptoQuotation quotationOutOfRange = new CryptoQuotation(CryptoCurrency.ADAUSDT, 0.05d, 19d,LocalDateTime.now());

        assertThatThrownBy(
                () -> marketOrder.beginAnOperationBy(anotherInvestmentAccount, quotationOutOfRange))
                .isInstanceOf(NotSuitablePriceException.class)
                .hasMessage("The current price of the asset does not fulfil the expectations from the emitter");
    }

    @Test
    @DisplayName("When an account wants to begin purchase operation and the current price is above the desired price a Cancelled Operation is issued for emitter and transactor")
    void testBeginPurchaseOperationWithNotSuitablePrice(){
        InvestmentAccount investmentAccount = new InvestmentAccount(anyInvestor());
        InvestmentAccount anotherInvestmentAccount = new InvestmentAccount(anyInvestor());
        MarketOrder marketOrder = anyPurchaseMarketOrderIssuedByWithDesiredPrice(investmentAccount,20d);

        CryptoQuotation quotationOutOfRange = new CryptoQuotation(CryptoCurrency.BTCUSDT, 0.06d, 21d,LocalDateTime.now());

        assertThatThrownBy(
                () -> marketOrder.beginAnOperationBy(anotherInvestmentAccount, quotationOutOfRange))
                .isInstanceOf(NotSuitablePriceException.class)
                .hasMessage("The current price of the asset does not fulfil the expectations from the emitter");
    }

    private CryptoQuotation anySuitableQuotationFor(MarketOrder aMarketOrder) {
        return new CryptoQuotation(aMarketOrder.getCryptoCurrency(), aMarketOrder.getDesiredPrice(),aMarketOrder.getDesiredPrice(), LocalDateTime.now());
    }

}
