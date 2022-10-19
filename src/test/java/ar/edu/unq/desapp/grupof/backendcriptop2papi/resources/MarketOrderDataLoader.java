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

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    InvestorDataLoader investorDataLoader;

    public MarketOrder loadAnyMarketOrderWithType(OrderType orderType, Double price, CryptoCurrency cryptoCurrency){
        InvestmentAccount anyInvestmentAccount = investorDataLoader.loadAnyInvestorAndGetAccount();

        MarketOrder createdMarketOrder =
                new MarketOrder(cryptoCurrency, anyInvestmentAccount, 1d, price, orderType, price, LocalDateTime.now());

        anyInvestmentAccount.placeMarketOrder(createdMarketOrder);

        return orderRepository.save(createdMarketOrder);


    }
}
