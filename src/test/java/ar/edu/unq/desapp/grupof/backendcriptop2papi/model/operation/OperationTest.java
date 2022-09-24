package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCancellationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.anyInvestor;
import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderTestResource.anyMarketOrderIssuedBy;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    private InvestmentAccount partyAccount;
    private InvestmentAccount counterPartyAccount;
    private MarketOrder aMarketOrder;
    private Operation anOperation;
    private CryptoQuotation aQuotation;


    @BeforeEach
    void initialize() {
        partyAccount = new InvestmentAccount(anyInvestor());
        counterPartyAccount = new InvestmentAccount(anyInvestor());
        aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        aQuotation = new CryptoQuotation("USDT",1d,20.5d, LocalDateTime.now());
        anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount, aQuotation);
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
        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.NEW);
    }

    @Test
    @DisplayName("When an operation is transacted by counter party, its status is changed to InProgressStatus")
    void testFirstTransaction() {
        Transaction generatedTransaction = anOperation.transact(counterPartyAccount, aQuotation);

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.IN_PROGRESS);
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(counterPartyAccount);
    }

    @Test
    @DisplayName("When an operation is transacted by party, its status is completed")
    void testSecondTransaction() {
        anOperation.transact(counterPartyAccount, aQuotation);

        Transaction generatedTransaction = anOperation.transact(partyAccount, aQuotation);

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.COMPLETED);
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(partyAccount);
    }

    @Test
    @DisplayName("When an operation is cancelled by an account, its status is canceled")
    void testCancelOperation() {
        anOperation.cancelBy(partyAccount);

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.CANCELLED);
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

        assertThat(anOperation.getTransactions().size()).isEqualTo(1);
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
        anOperation.changeStatusTo(OperationStatus.COMPLETED);

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
        anOperation.changeStatusTo(OperationStatus.COMPLETED);

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
        anOperation.changeStatusTo(OperationStatus.IN_PROGRESS);

        Assertions.assertThat(anOperation.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("An operation is not completed when its status CancelledStatus Status")
    void whenAnOperationHasCancelledStatusItIsNotCompleted() {
        anOperation.changeStatusTo(OperationStatus.CANCELLED);

        Assertions.assertThat(anOperation.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("When an operation was originated by sales order, the first transaction has as destination address the counterparty's crypto wallet")
    void testSalesOrderDestinationAddress() {
        Transaction transaction = anOperation.transact(counterPartyAccount, aQuotation);

        assertThat(transaction.getDestinationAddress()).isEqualTo(anOperation.getCounterparty().getInvestor().getCryptoWalletAddress());
    }

    @Test
    @DisplayName("When an operation was originated by purchase order, the first transaction has as destination address the counterparty's mercado pago cvu")
    void testPurchaseOrderDestinationAddress() {
        aMarketOrder.setOrderType(new PurchaseOrder());
        Transaction transaction = anOperation.transact(counterPartyAccount, aQuotation);

        assertThat(transaction.getDestinationAddress()).isEqualTo(anOperation.getCounterparty().getInvestor().getMercadoPagoCVU());
    }

    @Test
    @DisplayName("The second transaction's destination address is not applicable")
    void testDestinationAddressOfSecondTransaction() {
        anOperation.transact(counterPartyAccount, aQuotation);

        Transaction transaction = anOperation.transact(partyAccount, aQuotation);

        assertThat(transaction.getDestinationAddress()).isEqualTo("N/A");
    }

    @Test
    @DisplayName("A completed operation cannot be transacted")
    void testCannotTransactACompletedOperation() {
        // Arrange
        anOperation.transact(counterPartyAccount, aQuotation);
        anOperation.transact(partyAccount, aQuotation);

        // Act & Assert

        Assertions.assertThatThrownBy(() -> anOperation.transact(counterPartyAccount, aQuotation))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted because its status is COMPLETED");
    }

    @Test
    @DisplayName("A cancelled operation cannot be transacted")
    void testCannotTransactACancelledOperation() {
        // Arrange
        anOperation.cancelBy(counterPartyAccount);

        // Act & Assert

        Assertions.assertThatThrownBy(() -> anOperation.transact(partyAccount, aQuotation))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted because its status is CANCELLED");
    }

    @Test
    @DisplayName("An operation can not be transacted by a third party")
    void testCanNotBeTransactedByThirdParty() {
        var thirdPartyAccount = new InvestmentAccount(anyInvestor());

        Assertions.assertThatThrownBy(()->anOperation.transact(thirdPartyAccount, aQuotation))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The operation cannot be transacted by a third party");
    }

    @Test
    @DisplayName("An operation cannot be transacted by party when its status is New Operation Status")
    void testCanNotBeTransactedByPartyWhenNew() {

        Assertions.assertThatThrownBy(() -> anOperation.transact(partyAccount,aQuotation))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The party cannot transact until the counter party transacts");
    }
    @Test
    @DisplayName("An operation cannot be transacted by counterparty when its status is In Progress Status")
    void testCanNotBeTransactedByCounterPartyWhenInProgress() {
        anOperation.transact(counterPartyAccount,aQuotation);
        Assertions.assertThatThrownBy(() -> anOperation.transact(counterPartyAccount,aQuotation))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("The counterparty cannot transact once it has already transacted");
    }

    @Test
    @DisplayName("An operation can be canceled by system")
    void testCancelBySystem() {
        anOperation.systemCancel();

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.CANCELLED);
    }

    @Test
    @DisplayName("An operation can not be cancelled by system when its status is completed")
    void testCanNotCancelBySystemWhenIsCompleted() {
        anOperation.changeStatusTo(OperationStatus.COMPLETED);

        Assertions.assertThatThrownBy(() -> anOperation.systemCancel())
                .isInstanceOf(OperationNotCancellableException.class);
    }

    @Test
    @DisplayName("An operation can not be cancelled by system when its status is cancelled")
    void testCanNotCancelBySystemWhenIsCancelled() {
        anOperation.changeStatusTo(OperationStatus.CANCELLED);

        Assertions.assertThatThrownBy(() -> anOperation.systemCancel())
                .isInstanceOf(OperationNotCancellableException.class);
    }

}
