package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InvestmentAccountTest {

    private InvestmentAccount investmentAccount;
    private InvestmentAccount anotherInvestmentAccount;
    private final Investor ANY_INVESTOR = new Investor("asdasdad", "asddsadsa", "sadsaddas@gmail.com", "11233312344", "Nico123!", "1234567891234567891212", "12345678");

    @BeforeEach
    void setUp() {
        investmentAccount = new InvestmentAccount(ANY_INVESTOR);
        anotherInvestmentAccount = new InvestmentAccount(ANY_INVESTOR);
    }

    @Test
    @DisplayName("When account is created, it belongs to an investor")
    void testAccountCreationWithInvestor() {
        InvestmentAccount investmentAccount = new InvestmentAccount(ANY_INVESTOR);

        assertThat(investmentAccount.getInvestor()).isEqualTo(ANY_INVESTOR);
    }

    @Test
    @DisplayName("When account is created, its order list is empty")
    void testAccountDoesNotContainOrdersWhenIsCreated() {
        InvestmentAccount investmentAccount = new InvestmentAccount(ANY_INVESTOR);

        assertThat(investmentAccount.getMarketOrders()).isEmpty();
    }

    @Test
    @DisplayName("An account can place a market order")
    void testPlaceAnOrder(){
        InvestmentAccount investmentAccount = new InvestmentAccount(ANY_INVESTOR);

        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(investmentAccount);

        investmentAccount.placeMarketOrder(aMarketOrder);

        assertThat(investmentAccount.getMarketOrders()).contains(aMarketOrder);
    }

    private MarketOrder anyMarketOrderIssuedBy(InvestmentAccount investmentAccount) {
        Double desiredPrice = 20.5d;
        Double actualPrice = 20d;
        SalesOrder orderType = new SalesOrder();
        LocalDateTime aDateTime = LocalDateTime.now();

        return new MarketOrder("BNBUSDT", investmentAccount, 0.1d, desiredPrice, orderType, actualPrice, aDateTime);
    }

    @Test
    @DisplayName("An account can apply for a market order")
    void testAccountCanApplyForAMarketOrder() {
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(investmentAccount);
        investmentAccount.placeMarketOrder(aMarketOrder);

        anotherInvestmentAccount.applyFor(aMarketOrder);

        assertThat(aMarketOrder.getIsTaken()).isTrue();
    }

    @Test
    @DisplayName("When an account applies for an order, an operation is placed in both accounts")
    void testAccountAppliesForAnOrder() {
        MarketOrder aMarketOrder = anyMarketOrderIssuedBy(investmentAccount);
        investmentAccount.placeMarketOrder(aMarketOrder);

        anotherInvestmentAccount.applyFor(aMarketOrder);

        assertThat(investmentAccount.getOperations()).hasSize(1);
        assertThat(anotherInvestmentAccount.getOperations()).hasSize(1);
    }

    @Test
    @DisplayName("When an account is created, it does not contain performed operations")
    void testCreatedAccountDoesNotContainOperations() {
        assertThat(investmentAccount.getOperations()).isEmpty();
    }

}
