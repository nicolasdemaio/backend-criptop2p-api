package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.PurchaseOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.SalesOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.InvestorService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("prod") // TODO: Ver si a la hora de deployar, sube estos datos tambien a Heroku.
    // En el caso que no, ver como hacer de setearle a mano el profile
class DataLoader implements CommandLineRunner {

    private InvestorService investorService;
    private QuotationService quotationService;
    private InvestmentAccountRepository investmentAccountRepository;
    private OperationRepository operationRepository;
    private OrderRepository orderRepository;
    private OperationStatusRepository operationStatusRepository;
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    public DataLoader(InvestorService investorService, QuotationService quotationService, InvestmentAccountRepository investmentAccountRepository, OperationRepository operationRepository, OrderRepository orderRepository, OperationStatusRepository operationStatusRepository, OrderTypeRepository orderTypeRepository) {
        this.investorService = investorService;
        this.quotationService = quotationService;
        this.investmentAccountRepository = investmentAccountRepository;
        this.operationRepository = operationRepository;
        this.orderRepository = orderRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.operationStatusRepository = operationStatusRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        loadOrderTypes();
        loadOperationStatus();

        loadInvestors();

        List<MarketOrder> createdOrders = loadMarketOrders();

        loadOperationsForOrders(createdOrders);
    }

    private void loadOrderTypes() {
        orderTypeRepository.saveAll(
                List.of(
                        new PurchaseOrder(),
                        new SalesOrder()));
    }

    private void loadOperationStatus() {
        operationStatusRepository.saveAll(
                List.of(
                        new CancelledStatus(),
                        new CompletedStatus(),
                        new NewOperationStatus(),
                        new InProgressStatus()));
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
        MarketOrder evenAnotherOrder = saveMarketOrderFor(accounts.get(2), CryptoCurrency.AUDIOUSDT, new PurchaseOrder());

        return List.of(createdOrder, anotherOrder, yetAnotherOrder, evenAnotherOrder);
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
