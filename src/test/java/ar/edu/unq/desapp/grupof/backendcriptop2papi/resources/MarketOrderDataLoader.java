package ar.edu.unq.desapp.grupof.backendcriptop2papi.resources;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OrderRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MarketOrderDataLoader {

    CryptoCurrency anyCrypto = CryptoCurrency.AUDIOUSDT;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    InvestorDataLoader investorDataLoader;
    @Autowired
    QuotationService quotationService;

    public MarketOrder loadAnyMarketOrderWithType(OrderType orderType){
        InvestmentAccount anyInvestmentAccount = investorDataLoader.loadAnyInvestorAndGetAccount();
        Double currentPriceOfCrypto = quotationService.getCryptoQuotation(anyCrypto).getPriceInPesos();

        MarketOrder createdMarketOrder =
                new MarketOrder(anyCrypto, anyInvestmentAccount, 1d, currentPriceOfCrypto, orderType, currentPriceOfCrypto, LocalDateTime.now());

        anyInvestmentAccount.placeMarketOrder(createdMarketOrder);

        return orderRepository.save(createdMarketOrder);


    }
}
