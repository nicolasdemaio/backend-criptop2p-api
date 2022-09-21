package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCancellationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotCancellableException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.anyInvestor;
import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderTestResource.anyMarketOrderIssuedBy;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    private InvestmentAccount partyAccount;
    private InvestmentAccount counterPartyAccount;
    private MarketOrder aMarketOrder;
    private Operation anOperation;


    @BeforeEach
    void initialize() {
        partyAccount = new InvestmentAccount(anyInvestor());
        counterPartyAccount = new InvestmentAccount(anyInvestor());
        aMarketOrder = anyMarketOrderIssuedBy(partyAccount);
        anOperation = aMarketOrder.beginAnOperationBy(counterPartyAccount);
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
        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.NEW);
    }

    @Test
    @DisplayName("When an operation is transacted by counter party, its status is changed to InProgressStatus")
    void testFirstTransaction() {
        Transaction generatedTransaction = anOperation.transact();

        assertThat(anOperation.getStatus()).isEqualTo(OperationStatus.IN_PROGRESS);
        assertThat(generatedTransaction.getPartyAccount()).isEqualTo(counterPartyAccount);
    }

    @Test
    @DisplayName("When an operation is transacted by party, its status is completed")
    void testSecondTransaction() {
        anOperation.transact();

        Transaction generatedTransaction = anOperation.transact();

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

}
