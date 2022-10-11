package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidObjectException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.InvestorService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OrderService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
class DataLoader implements CommandLineRunner {

    private InvestorService investorService;
    private QuotationService quotationService;
    private InvestmentAccountRepository investmentAccountRepository;
    private OperationRepository operationRepository;
    private OrderRepository orderRepository;
    private OperationStatusRepository operationStatusRepository;

    @Autowired
    public DataLoader(InvestorService investorService, QuotationService quotationService, InvestmentAccountRepository investmentAccountRepository, OperationRepository operationRepository, OrderRepository orderRepository, OperationStatusRepository operationStatusRepository) {
        this.investorService = investorService;
        this.quotationService = quotationService;
        this.investmentAccountRepository = investmentAccountRepository;
        this.operationRepository = operationRepository;
        this.orderRepository = orderRepository;
        this.operationStatusRepository = operationStatusRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        loadOperationStatus();

        loadInvestors();

        List<MarketOrder> createdOrders = loadMarketOrders();

        loadOperationsForOrders(createdOrders);
    }

    private void loadOperationStatus() {
        CancelledStatus cancelledStatus = new CancelledStatus();
        CompletedStatus completedStatus = new CompletedStatus();
        NewOperationStatus newStatus = new NewOperationStatus();
        InProgressStatus inProgressStatus = new InProgressStatus();

        operationStatusRepository.save(cancelledStatus);
        operationStatusRepository.save(completedStatus);
        operationStatusRepository.save(newStatus);
        operationStatusRepository.save(inProgressStatus);
    }

    private void loadOperationsForOrders(List<MarketOrder> createdOrders) {
        List<InvestmentAccount> accounts = investmentAccountRepository.findAll();

        applyForMarketOrderFrom(createdOrders.get(0), accounts.get(1));
        applyForMarketOrderFrom(createdOrders.get(1), accounts.get(2));
        applyForMarketOrderFrom(createdOrders.get(2), accounts.get(0));
    }

    private List<MarketOrder> loadMarketOrders() {
        List<InvestmentAccount> accounts = investmentAccountRepository.findAll();

        MarketOrder createdOrder = saveMarketOrderFor(accounts.get(0), CryptoCurrency.AUDIOUSDT, new SalesOrder());
        MarketOrder anotherOrder = saveMarketOrderFor(accounts.get(1), CryptoCurrency.AUDIOUSDT, new SalesOrder());
        MarketOrder yetAnotherOrder = saveMarketOrderFor(accounts.get(2), CryptoCurrency.AUDIOUSDT, new PurchaseOrder());

        return List.of(createdOrder, anotherOrder, yetAnotherOrder);
    }

    private void loadInvestors() {
        UserRegistrationForm anInvestor = new UserRegistrationForm("Pepe", "Juarez", "pepe.juarez@gmail.com", "Calle falsa 123", "Pepe123!", "1234567891234567891234", "12345678");
        UserRegistrationForm anotherInvestor = new UserRegistrationForm("Juan", "Perez", "j.perez@correo.com", "Calle falsa 456", "Juan123!", "4321123456789123456789", "56781234");
        UserRegistrationForm yetAnotherInvestor = new UserRegistrationForm("John", "Doe", "j.doe@yahoo.com", "Calle falsa 789", "John123!", "1212345678912345678934", "34561278");

        List.of(anInvestor, anotherInvestor, yetAnotherInvestor).forEach(account -> investorService.registerUser(account));
    }

    private void applyForMarketOrderFrom(MarketOrder anOrder, InvestmentAccount anAccount) {
        CryptoQuotation cryptoQuotation = quotationService.getCryptoQuotation(anOrder.getCryptoCurrency());
        Operation generatedOperation = anAccount.applyFor(anOrder, cryptoQuotation);
        orderRepository.save(generatedOperation.getSourceOfOrigin());
        operationRepository.save(generatedOperation);

    }

    private MarketOrder saveMarketOrderFor(InvestmentAccount investmentAccount, CryptoCurrency cryptoCurrency, OrderType orderType) {
        Double currentPriceOfCrypto = quotationService.getCryptoQuotation(cryptoCurrency).getPriceInPesos();

        MarketOrder createdMarketOrder =
                new MarketOrder(cryptoCurrency, investmentAccount, 1d, currentPriceOfCrypto, orderType, currentPriceOfCrypto, LocalDateTime.now());

        investmentAccount.placeMarketOrder(createdMarketOrder);

        return orderRepository.save(createdMarketOrder);
    }
}
