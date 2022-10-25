package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.AssetStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvestorNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeStatisticsServiceTest {

    @Mock
    private InvestmentAccountRepository investmentAccountRepository;

    @Mock
    private QuotationService quotationService;

    @InjectMocks
    private TradeStatisticsService tradeStatisticsService;

    @Test
    void testGetReportThrowsExceptionForInvalidInvestmentAccountId() {
        Assertions.assertThatThrownBy(() ->
                tradeStatisticsService.getStatisticsFrom(1L))
                .isInstanceOf(InvestorNotFoundException.class);
    }

    @Test
    void testGetTradeStatisticsOfAccount() {
        List<Operation> operations = List.of();
        InvestmentAccount account = Mockito.mock(InvestmentAccount.class);
        when(account.getOperations()).thenReturn(operations);
        when(investmentAccountRepository.findInvestmentAccountByInvestor(1L)).thenReturn(account);

        InvestorStatistic investorStatistic =
                tradeStatisticsService.getStatisticsFrom(1L);

        assertThat(investorStatistic.getAssetStatistics()).isEmpty();
        assertThat(investorStatistic.getTotalQuantityInDollars()).isZero();
        assertThat(investorStatistic.getTotalQuantityInPesos()).isZero();
        assertThat(investorStatistic.getTimestamp()).isNotNull();
    }

    @Test
    void testGetTradeStatisticsOfAccountWith1CompletedOperation() {
        Operation operation = getMockedOperation();

        List<Operation> operations = List.of(operation);
        InvestmentAccount account = Mockito.mock(InvestmentAccount.class);
        when(account.getOperations()).thenReturn(operations);
        when(investmentAccountRepository.findInvestmentAccountByInvestor(1L)).thenReturn(account);

        InvestorStatistic investorStatistic =
                tradeStatisticsService.getStatisticsFrom(1L);

        assertThat(investorStatistic.getAssetStatistics()).isNotEmpty();
        assertThat(investorStatistic.getTotalQuantityInDollars()).isOne();
        assertThat(investorStatistic.getTotalQuantityInPesos()).isOne();
        assertThat(investorStatistic.getTimestamp()).isNotNull();
    }

    private Operation getMockedOperation() {
        var order = Mockito.mock(MarketOrder.class);
        var cryptoQuotation = Mockito.mock(CryptoQuotation.class);

        when(cryptoQuotation.getPriceInDollars()).thenReturn(1d);
        when(cryptoQuotation.getPriceInPesos()).thenReturn(1d);
        when(cryptoQuotation.getCryptoCurrency()).thenReturn(CryptoCurrency.AAVEUSDT);

        Operation operation = Mockito.mock(Operation.class);
        when(operation.isCompleted()).thenReturn(true);

        when(order.getNominalQuantity()).thenReturn(1d);

        when(operation.getSourceOfOrigin()).thenReturn(order);
        when(operation.getCryptoQuotation()).thenReturn(cryptoQuotation);
        when(quotationService.getCryptoQuotation(CryptoCurrency.AAVEUSDT))
                .thenReturn(cryptoQuotation);
        return operation;
    }

}
