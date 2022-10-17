package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OrderForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.CryptoQuotationRepository;
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
    private ModelMapper modelMapper;
    private QuotationService quotationService;
    private InvestorService investorService;
    private CryptoQuotationRepository quotationRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, InvestorService investorService, InvestmentAccountRepository investmentAccountRepository, QuotationService quotationService, OperationRepository operationRepository, CryptoQuotationRepository quotationRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.investorService = investorService;
        this.investmentAccountRepository = investmentAccountRepository;
        this.quotationService = quotationService;
        this.operationRepository = operationRepository;
        this.quotationRepository = quotationRepository;
    }

    public List<MarketOrderDTO> getActiveOrders() {
        List<MarketOrder> orders = orderRepository.findActiveOrders();
        return orders.stream().map(order -> MarketOrderDTO.fromModel(order)).toList();
    }

    @Transactional
    public MarketOrderDTO placeMarketOrder(OrderForm form, Authentication authentication){
        InvestorDTO loggedInvestor = investorService.authenticatedUser(authentication);
        InvestmentAccount accountFromUser = investmentAccountRepository.findInvestmentAccountByInvestor(loggedInvestor.getId());
        Double currentPriceOfCrypto = quotationService.getCryptoQuotation(form.getCryptoCurrency()).getPriceInPesos();

        MarketOrder createdMarketOrder = form.createMarketOrder(accountFromUser, currentPriceOfCrypto);
        accountFromUser.placeMarketOrder(createdMarketOrder);

        investmentAccountRepository.save(accountFromUser);

        return MarketOrderDTO.fromModel(createdMarketOrder);
    }

    @Transactional
    public OperationDTO applyForOrder(Long aMarketOrderId, Authentication authentication){
        InvestorDTO loggedInvestor = investorService.authenticatedUser(authentication);
        MarketOrder marketOrder = orderRepository.findById(aMarketOrderId).orElseThrow(() -> new RuntimeException("Cambiarlo"));
        InvestmentAccount accountFromUser = investmentAccountRepository.findInvestmentAccountByInvestor(loggedInvestor.getId());
        CryptoQuotation cryptoQuotation = quotationService.getCryptoQuotation(marketOrder.getCryptoCurrency());

        Operation generatedOperation = accountFromUser.applyFor(marketOrder, cryptoQuotation);

        generatedOperation = operationRepository.save(generatedOperation);

        return OperationDTO.fromModel(generatedOperation);
    }
}
