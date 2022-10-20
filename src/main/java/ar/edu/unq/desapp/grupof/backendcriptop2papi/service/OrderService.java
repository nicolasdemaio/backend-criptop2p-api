package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OrderForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OrderNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OperationRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OperationRepository operationRepository;
    private InvestmentAccountRepository investmentAccountRepository;
    private QuotationService quotationService;
    private ContextService contextService;

    @Autowired
    public OrderService(OrderRepository orderRepository, InvestmentAccountRepository investmentAccountRepository, QuotationService quotationService, OperationRepository operationRepository,ContextService contextService) {
        this.orderRepository = orderRepository;
        this.investmentAccountRepository = investmentAccountRepository;
        this.quotationService = quotationService;
        this.operationRepository = operationRepository;
        this.contextService = contextService;
    }

    public List<MarketOrderDTO> getActiveOrders() {
        List<MarketOrder> orders = orderRepository.findActiveOrders();
        return orders.stream().map(MarketOrderDTO::fromModel).toList();
    }

    @Transactional
    public MarketOrderDTO placeMarketOrder(OrderForm form, Authentication authentication){
        InvestmentAccount account = contextService.getCurrentAccount(authentication);
        Double currentPriceOfCrypto = quotationService.getCryptoQuotation(form.getCryptoCurrency()).getPriceInPesos();

        MarketOrder createdMarketOrder = form.createMarketOrder(account, currentPriceOfCrypto);
        account.placeMarketOrder(createdMarketOrder);

        investmentAccountRepository.save(account);

        return MarketOrderDTO.fromModel(createdMarketOrder);
    }

    @Transactional
    public OperationDTO applyForOrder(Long aMarketOrderId, Authentication authentication){
        InvestmentAccount account = contextService.getCurrentAccount(authentication);
        MarketOrder marketOrder = orderRepository.findById(aMarketOrderId).orElseThrow(OrderNotFoundException::new);
        CryptoQuotation cryptoQuotation = quotationService.getCryptoQuotation(marketOrder.getCryptoCurrency());

        Operation generatedOperation = account.applyFor(marketOrder, cryptoQuotation);

        generatedOperation = operationRepository.save(generatedOperation);

        return OperationDTO.fromModel(generatedOperation);
    }
}
