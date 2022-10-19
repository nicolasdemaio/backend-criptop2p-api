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
@Profile("dev")
class TestingDataLoader implements CommandLineRunner {

    private OperationStatusRepository operationStatusRepository;
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    public TestingDataLoader(OperationStatusRepository operationStatusRepository, OrderTypeRepository orderTypeRepository) {
        this.orderTypeRepository = orderTypeRepository;
        this.operationStatusRepository = operationStatusRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        loadOrderTypes();
        loadOperationStatus();
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

}
