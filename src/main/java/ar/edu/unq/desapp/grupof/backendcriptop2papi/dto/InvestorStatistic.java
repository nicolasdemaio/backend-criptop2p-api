package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InvestorStatistic {
    private LocalDateTime timestamp;
    private Double totalQuantityInDollars;
    private Double totalQuantityInPesos;
    private List<AssetStatistic> assetStatistics;
}
