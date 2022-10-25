package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.TradeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/stats")
public class TradeStatisticsController {

    private TradeStatisticsService tradeStatisticsService;

    @Autowired
    public TradeStatisticsController(TradeStatisticsService tradeStatisticsService) {
        this.tradeStatisticsService = tradeStatisticsService;
    }

    @PostMapping(path = "/{investorId}")
    public ResponseEntity<InvestorStatistic> getStatisticsFrom(@PathVariable Long investorId, @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        return ResponseEntity.status(HttpStatus.OK).body(tradeStatisticsService.getStatisticsFrom(investorId, from, to));
    }

}
