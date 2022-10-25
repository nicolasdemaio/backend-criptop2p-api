package ar.edu.unq.desapp.grupof.backendcriptop2papi.controller;


import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.TradeStatisticsService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice.TradeStatisticsController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

    @Mock
    private TradeStatisticsService tradeStatisticsService;

    @InjectMocks
    private TradeStatisticsController tradeStatisticsController;

    @Test
    void getStatisticsTest() {
        Long investorId = 1L;
        InvestorStatistic investorStatistic = Mockito.mock(InvestorStatistic.class);

        Mockito.when(tradeStatisticsService.getStatisticsFrom(investorId)).thenReturn(investorStatistic);

        ResponseEntity<InvestorStatistic> response = tradeStatisticsController.getStatisticsFrom(investorId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
