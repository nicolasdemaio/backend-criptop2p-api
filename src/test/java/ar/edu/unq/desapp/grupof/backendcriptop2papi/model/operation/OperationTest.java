package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCancellationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidDateException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.PurchaseOrder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.anyInvestor;
import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderTestResource.anyMarketOrderIssuedBy;
import static org.assertj.core.api.Assertions.assertThat;

class OperationTest {

    private InvestmentAccount partyAccount;
    private InvestmentAccount counterPartyAccount;
    private MarketOrder aMarketOrder;
    private Operation anOperation;
    private CryptoQuotation aQuotation;
    private LocalDateTime now;

    @BeforeEach
    void initialize() {
        partyAccount = new InvestmentAccount(anyInvestor());
        counterPartyAccount = new InvestmentAccount(anyInvestor());
        aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        aQuotation = new CryptoQuotation(CryptoCurrency.BTCUSDT,1d,20.5d, LocalDateTime.now());
        anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount, aQuotation);
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor test")
    void constructorTest(){
        MarketOrder marketOrder = anyMarketOrderIssuedBy(partyAccount);
        Operation anOperation = marketOrder.beginAnOperationBy(counterPartyAccount, aQuotation);

        assertThat(anOperation.getParty()).isEqualTo(partyAccount);
        assertThat(anOperation.getCounterparty()).isEqualTo(counterPartyAccount);
        assertThat(anOperation.getSourceOfOrigin()).isEqualTo(marketOrder);
        assertThat(anOperation.getTransactions()).isEmpty();
    }

    @Test
    @DisplayName("When an Operation is created its status is NewOperationStatus")
    void newStatusTest(){
        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.newOperation());
    }

    @Test
    @DisplayName("When an operation is transacted by counter party, its status is changed to InProgressStatus")
    void testFirstTransaction() {
        Transaction generatedTransaction = anOperation.transact(counterPartyAccount, LocalDateTime.now());

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.inProgress());
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(counterPartyAccount);
    }

    @Test
    @DisplayName("When an operation is transacted by party, its status is completed")
    void testSecondTransaction() {
        anOperation.transact(counterPartyAccount, LocalDateTime.now());

        Transaction generatedTransaction = anOperation.transact(partyAccount, LocalDateTime.now());

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.completed());
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(partyAccount);
    }

    @Test
    @DisplayName("When an operation is cancelled by an account, its status is canceled")
    void testCancelOperation() {
        anOperation.cancelBy(partyAccount);

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.cancelled());
    }

    @Test
    @DisplayName("When an operation is cancelled by an account, this account loses 20 points")
    void testCancelOperationPoints() {
        anOperation.cancelBy(partyAccount);

        int expectedPoints = -20;

        assertThat(partyAccount.getPoints()).isEqualTo(expectedPoints);
    }

    @Test
    @DisplayName("When an operation is cancelled by an account, the operation has a new transaction")
    void testCancelOperationTransactions() {
        Transaction generatedTransaction = anOperation.cancelBy(partyAccount);

        assertThat(anOperation.getTransactions()).hasSize(1);
        assertThat(generatedTransaction.getAction()).isEqualTo("Cancel");
    }

    @Test
    @DisplayName("An operation can not be cancelled twice")
    void testCanNotCancelTwice() {
        anOperation.cancelBy(partyAccount);

        Assertions.assertThatThrownBy(() -> anOperation.cancelBy(partyAccount))
                        .isInstanceOf(OperationNotCancellableException.class);
    }

    @Test
    @DisplayName("An operation can not be cancelled when its status is completed")
    void testCanNotCancelWhenIsCompleted() {
        anOperation.changeStatusTo(OperationStatus.completed());

        Assertions.assertThatThrownBy(() -> anOperation.cancelBy(partyAccount))
                .isInstanceOf(OperationNotCancellableException.class);
    }

    @Test
    @DisplayName("An operation can not be cancelled by a third party")
    void testCanNotBeCancelledByThirdParty() {
        var thirdPartyAccount = new InvestmentAccount(anyInvestor());

        Assertions.assertThatThrownBy(()->anOperation.cancelBy(thirdPartyAccount))
                .isInstanceOf(InvalidCancellationException.class);
    }

    @Test
    @DisplayName("An operation is completed when its status is Completed Status")
    void whenAnOperationHasStatusCompletedItIsCompleted() {
        anOperation.changeStatusTo(OperationStatus.completed());

        Assertions.assertThat(anOperation.isCompleted()).isTrue();
    }

    @Test
    @DisplayName("An operation is not completed when its status is New Operation Status")
    void whenAnOperationHasNewOperationStatusItIsNotCompleted() {
        Assertions.assertThat(anOperation.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("An operation is not completed when its status is In Progress Status")
    void whenAnOperationHasInProgressStatusItIsNotCompleted() {
        anOperation.changeStatusTo(OperationStatus.inProgress());

        Assertions.assertThat(anOperation.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("An operation is not completed when its status CancelledStatus Status")
    void whenAnOperationHasCancelledStatusItIsNotCompleted() {
        anOperation.changeStatusTo(OperationStatus.cancelled());

        Assertions.assertThat(anOperation.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("When an operation was originated by sales order, the first transaction has as destination address the counterparty's crypto wallet")
    void testSalesOrderDestinationAddress() {
        Transaction transaction = anOperation.transact(counterPartyAccount, LocalDateTime.now());

        assertThat(transaction.getDestinationAddress()).isEqualTo(anOperation.getCounterparty().getInvestor().getCryptoWalletAddress());
    }

    @Test
    @DisplayName("When an operation was originated by purchase order, the first transaction has as destination address the counterparty's mercado pago cvu")
    void testPurchaseOrderDestinationAddress() {
        aMarketOrder.setOrderType(new PurchaseOrder());
        Transaction transaction = anOperation.transact(counterPartyAccount, LocalDateTime.now());

        assertThat(transaction.getDestinationAddress()).isEqualTo(anOperation.getCounterparty().getInvestor().getMercadoPagoCVU());
    }

    @Test
    @DisplayName("The second transaction's destination address is not applicable")
    void testDestinationAddressOfSecondTransaction() {
        anOperation.transact(counterPartyAccount, LocalDateTime.now());

        Transaction transaction = anOperation.transact(partyAccount, now);

        assertThat(transaction.getDestinationAddress()).isEqualTo("N/A");
    }

    @Test
    @DisplayName("A completed operation cannot be transacted")
    void testCannotTransactACompletedOperation() {
        // Arrange
        anOperation.transact(counterPartyAccount, now);
        anOperation.transact(partyAccount, now);

        // Act & Assert

        Assertions.assertThatThrownBy(() -> anOperation.transact(counterPartyAccount, now))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted because its status is COMPLETED");
    }


    @Test
    @DisplayName("A cancelled operation cannot be transacted")
    void testCannotTransactACancelledOperation() {
        // Arrange
        anOperation.cancelBy(counterPartyAccount);

        // Act & Assert

        Assertions.assertThatThrownBy(() -> anOperation.transact(partyAccount, now))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted because its status is CANCELLED");
    }

    @Test
    @DisplayName("An operation can not be transacted by a third party")
    void testCanNotBeTransactedByThirdParty() {
        var thirdPartyAccount = new InvestmentAccount(anyInvestor());

        Assertions.assertThatThrownBy(()->anOperation.transact(thirdPartyAccount, now))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted by a third party");
    }

    @Test
    @DisplayName("An operation cannot be transacted by party when its status is New Operation Status")
    void testCanNotBeTransactedByPartyWhenNew() {

        Assertions.assertThatThrownBy(() -> anOperation.transact(partyAccount, now))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The party cannot transact until the counter party transacts");
    }
    @Test
    @DisplayName("An operation cannot be transacted by counterparty when its status is In Progress Status")
    void testCanNotBeTransactedByCounterPartyWhenInProgress() {
        anOperation.transact(counterPartyAccount, LocalDateTime.now());
        Assertions.assertThatThrownBy(() -> anOperation.transact(counterPartyAccount, now))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The counterparty cannot transact once it has already transacted");
    }

    @Test
    @DisplayName("An operation can be canceled by system")
    void testCancelBySystem() {
        anOperation.systemCancel();

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.cancelled());
    }

    @Test
    @DisplayName("An operation can not be cancelled by system when its status is completed")
    void testCanNotCancelBySystemWhenIsCompleted() {
        anOperation.changeStatusTo(OperationStatus.completed());

        Assertions.assertThatThrownBy(() -> anOperation.systemCancel())
                .isInstanceOf(OperationNotCancellableException.class);
    }

    @Test
    @DisplayName("An operation can not be cancelled by system when its status is cancelled")
    void testCanNotCancelBySystemWhenIsCancelled() {
        anOperation.changeStatusTo(OperationStatus.cancelled());

        Assertions.assertThatThrownBy(() -> anOperation.systemCancel())
                .isInstanceOf(OperationNotCancellableException.class);
    }

    @Test
    @DisplayName("When an operation is completed within the first 30 minutes, both accounts gain 10 points")
    void testOperationCompletedBefore30Minutes() {
        LocalDateTime dateTime = LocalDateTime.now();
        anOperation.transact(counterPartyAccount, dateTime);

        anOperation.transact(partyAccount, dateTime);

        Assertions.assertThat(partyAccount.getPoints()).isEqualTo(10);
        Assertions.assertThat(counterPartyAccount.getPoints()).isEqualTo(10);
    }

    @Test
    @DisplayName("When an operation is completed 30 minutes after being originated, both accounts gain 5 points")
    void testOperationCompletedAfter30Minutes() {
        LocalDateTime dateTime = LocalDateTime.now();
        anOperation.transact(counterPartyAccount, dateTime);

        anOperation.transact(partyAccount, dateTime.plusMinutes(31));

        Assertions.assertThat(partyAccount.getPoints()).isEqualTo(5);
        Assertions.assertThat(counterPartyAccount.getPoints()).isEqualTo(5);
    }

    @Test
    void whenOperationIsCreatedItsStatusIsActive() {
        assertThat(anOperation.isActive()).isTrue();
    }

    @Test
    void testWasOriginatedBetweenWithFromDateAfterToDateThrowsInvalidDateException(){
        LocalDateTime toTime = LocalDateTime.now();
        LocalDateTime fromTime = toTime.plusMinutes(20L);
        Assertions.assertThatThrownBy(() -> anOperation.wasOriginatedBetween(fromTime, toTime))
                .isInstanceOf(InvalidDateException.class)
                .hasMessage("The second date can not be before the first one");
    }

    @Test
    void testWasOriginatedBetweenWhenTheOperationIsBetweenThoseDates(){
        LocalDateTime fromTime = LocalDateTime.now().minusMinutes(20L);
        LocalDateTime toTime = LocalDateTime.now().plusMinutes(20L);

        Assertions.assertThat(anOperation.wasOriginatedBetween(fromTime,toTime)).isTrue();
    }

    @Test
    void testWasOriginatedBetweenWhenTheOperationIsNotBetweenThoseDates(){
        LocalDateTime fromTime = LocalDateTime.now().minusMinutes(20L);
        LocalDateTime toTime = fromTime.plusMinutes(1L);

        Assertions.assertThat(anOperation.wasOriginatedBetween(fromTime,toTime)).isFalse();
    }




}
