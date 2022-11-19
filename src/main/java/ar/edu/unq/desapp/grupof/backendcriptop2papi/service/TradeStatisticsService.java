package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.AssetStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorStatistic;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvestorNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeStatisticsService {

    private InvestmentAccountRepository investmentAccountRepository;
    private QuotationService quotationService;

    @Autowired
    public TradeStatisticsService(InvestmentAccountRepository investmentAccountRepository, QuotationService quotationService){
        this.investmentAccountRepository = investmentAccountRepository;
        this.quotationService = quotationService;
    }

    /**
     * Generates a statistic report describing the traded volume from an investor
     * @param investorId Long
     * @param from LocalDateTime
     * @param to LocalDateTime
     * @return InvestorStatistic
     */
    @Transactional
    public InvestorStatistic getStatisticsFrom(Long investorId, LocalDate from, LocalDate to) {
        InvestmentAccount requestedAccount = investmentAccountRepository.findInvestmentAccountByInvestor(investorId);
        if (requestedAccount == null) throw new InvestorNotFoundException();

        return this.generateStatisticReportFor(requestedAccount, from, to);
    }

    private InvestorStatistic generateStatisticReportFor (InvestmentAccount investmentAccount, LocalDate from, LocalDate to) {
        List<Operation> completedOperations =
                investmentAccount
                        .getOperations()
                        .stream()
                        .filter(operation -> operation.isCompleted() && operation.wasOriginatedBetween(from, to))
                        .toList();

        Double totalQuantityInDollars = completedOperations.stream().mapToDouble(this::getQuantityInDollars).sum();
        Double totalQuantityInPesos = completedOperations.stream().mapToDouble(this::getQuantityInPesos).sum();
        List<AssetStatistic> assetStatistics = this.generateAssetStatistics(completedOperations);

        return new InvestorStatistic(LocalDateTime.now(), totalQuantityInDollars, totalQuantityInPesos, assetStatistics);
    }

    private List<AssetStatistic> generateAssetStatistics (List<Operation> operations) {
        Map<CryptoCurrency, AssetStatistic> assetStatistics = new EnumMap<> (CryptoCurrency.class);
        operations.forEach(operation -> updateAssetStatistics(assetStatistics, operation));
        return assetStatistics.values().stream().toList();
    }

    private void updateAssetStatistics(Map<CryptoCurrency, AssetStatistic> assetStatistics, Operation operation) {
        CryptoCurrency cryptoCurrency = operation.getCryptoQuotation().getCryptoCurrency();
        Double nominalQuantity = operation.getSourceOfOrigin().getNominalQuantity();
        if (assetStatistics.containsKey(cryptoCurrency)){
            AssetStatistic assetStatistic = assetStatistics.get(cryptoCurrency);
            assetStatistic.addNominalQuantity(nominalQuantity);
        } else {
            CryptoQuotation currentQuotation = this.quotationService.getCryptoQuotation(cryptoCurrency);
            assetStatistics.put(cryptoCurrency,
                    new AssetStatistic(
                            cryptoCurrency,
                            nominalQuantity,
                            currentQuotation.getPriceInDollars(),
                            currentQuotation.getPriceInPesos()
                    )
            );
        }
    }

    private Double getQuantityInDollars(Operation operation){
        return operation.getSourceOfOrigin().getNominalQuantity() * operation.getCryptoQuotation().getPriceInDollars();
    }

    private Double getQuantityInPesos(Operation operation){
        return operation.getSourceOfOrigin().getNominalQuantity() * operation.getCryptoQuotation().getPriceInPesos();
    }
}
