package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OrderService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.TradeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/stats")
public class TradeStatisticsController {

    private TradeStatisticsService tradeStatisticsService;

    @Autowired
    public TradeStatisticsController(TradeStatisticsService tradeStatisticsService) {
        this.tradeStatisticsService = tradeStatisticsService;
    }

    @PostMapping(path = "/{investorId}")
    public ResponseEntity<InvestorStatistic> getStatisticsFrom(@PathVariable Long investorId) {
        return ResponseEntity.status(HttpStatus.OK).body(tradeStatisticsService.getStatisticsFrom(investorId));
    }

}
