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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradeStatisticsService {

    private InvestmentAccountRepository investmentAccountRepository;
    private QuotationService quotationService;

    @Autowired
    public TradeStatisticsService(InvestmentAccountRepository investmentAccountRepository, QuotationService quotationService){
        this.investmentAccountRepository = investmentAccountRepository;
        this.quotationService = quotationService;
    }

    public InvestorStatistic getStatisticsFrom(Long investorId) {
        InvestmentAccount requestedAccount = investmentAccountRepository.findInvestmentAccountByInvestor(investorId);
        if (requestedAccount == null) throw new InvestorNotFoundException();

        List<Operation> completedOperations =
                requestedAccount
                        .getOperations()
                        .stream()
                        .filter(operation -> operation.isCompleted())
                        .toList();

        Double totalQuantityInDollars = completedOperations.stream().mapToDouble(operation -> getQuantityInDollars(operation)).sum();
        Double totalQuantityInPesos = completedOperations.stream().mapToDouble(operation -> getQuantityInPesos(operation)).sum();


        // HashMap (CryptoCurrency -> AssetStatistic)
        Map<CryptoCurrency, AssetStatistic> assetStatistics = new HashMap<>();

        completedOperations.forEach(operation -> {
            CryptoCurrency cryptoCurrency = operation.getCryptoQuotation().getCryptoCurrency();
            CryptoQuotation currentQuotation = this.quotationService.getCryptoQuotation(cryptoCurrency);
            Double nominalQuantity = operation.getSourceOfOrigin().getNominalQuantity();
            if (assetStatistics.containsKey(cryptoCurrency)){
                AssetStatistic assetStatistic = assetStatistics.get(cryptoCurrency);
                assetStatistic.addNominalQuantity(nominalQuantity);
            } else {
                assetStatistics.put(cryptoCurrency,
                        new AssetStatistic(
                                cryptoCurrency,
                                nominalQuantity,
                                currentQuotation.getPriceInDollars(),
                                currentQuotation.getPriceInPesos()

                        )
                );
            }

        });



        return new InvestorStatistic(LocalDateTime.now(), totalQuantityInDollars, totalQuantityInPesos, assetStatistics.values().stream().toList());
    }

    private Double getQuantityInDollars(Operation operation){
        return operation.getSourceOfOrigin().getNominalQuantity() * operation.getCryptoQuotation().getPriceInDollars();
    }

    private Double getQuantityInPesos(Operation operation){
        return operation.getSourceOfOrigin().getNominalQuantity() * operation.getCryptoQuotation().getPriceInPesos();
    }
}
