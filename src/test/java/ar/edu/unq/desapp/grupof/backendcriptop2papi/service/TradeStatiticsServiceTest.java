package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.AssetStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeStatiticsServiceTest {

    @Mock
    private InvestmentAccountRepository investmentAccountRepository;

    @InjectMocks
    private TradeStatisticsService tradeStatisticsService;

    @Test
    void testGetReportThrowsExceptionForInvalidInvestmentAccountId() {
        Assertions.assertThatThrownBy(() ->
                tradeStatisticsService.getStatisticsFrom(1L, LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(InvestorNotFoundException.class);
    }

    @Test
    void testGetTradeStatiticsOfAccount() {
        List<Operation> operations = List.of();
        InvestmentAccount account = Mockito.mock(InvestmentAccount.class);
        when(account.getOperations()).thenReturn(operations);
        when(investmentAccountRepository.findInvestmentAccountByInvestor(1L)).thenReturn(account);

        InvestorStatistic investorStatistic =
                tradeStatisticsService.getStatisticsFrom(1L, LocalDateTime.now(), LocalDateTime.now());

        assertThat(investorStatistic.getAssetStatistics()).isEmpty();
        assertThat(investorStatistic.getTotalQuantityInDollars()).isZero();
        assertThat(investorStatistic.getTotalQuantityInPesos()).isZero();
        assertThat(investorStatistic.getTimestamp()).isNotNull();
    }

}
