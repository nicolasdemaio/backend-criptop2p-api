package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.TradeStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/stats")
public class TradeStatisticsController {

    private TradeStatisticsService tradeStatisticsService;

    @Autowired
    public TradeStatisticsController(TradeStatisticsService tradeStatisticsService) {
        this.tradeStatisticsService = tradeStatisticsService;
    }

    @Operation (summary = "Get the operated volume from an User")
    @PostMapping(path = "/{investorId}")
    public ResponseEntity<InvestorStatistic> getStatisticsFrom(@PathVariable Long investorId,
                                                               @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy")
                                                                       LocalDate from,
                                                               @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy")
                                                                           LocalDate to) {
        return ResponseEntity.status(HttpStatus.OK).body(tradeStatisticsService.getStatisticsFrom(investorId, from, to));
    }

}
