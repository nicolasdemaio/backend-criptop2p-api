package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.*;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.OperationStatus;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OperationRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.TransactionRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderTestResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    @Mock
    private OperationRepository operationRepository;
    @Mock
    private ContextService contextService;
    @Mock
    private Authentication authentication;
    @Mock
    private Operation operation;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private OperationService operationService;

    private final Long operationId = 1L;
    private MarketOrder marketOrder;
    private InvestmentAccount account;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        marketOrder = MarketOrderTestResource.anyMarketOrder();
        account = marketOrder.getEmitter();
        account.setId(1L);


        when(contextService.getCurrentAccount(authentication)).thenReturn(account);
        transaction = new Transaction(
                account,
                "N/A",
                "1234567891234",
                new CryptoQuotation(CryptoCurrency.BNBUSDT, 123d, 123d, LocalDateTime.now()),
                LocalDateTime.now(),
                marketOrder);
    }

    @Test
    void test_cancelOperationByLoggedInAccount() {
        when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));

        when(operation.cancelBy(account)).thenReturn(transaction);
        when(transactionRepository.save(any())).thenReturn(transaction);

        operationService.cancelOperationById(operationId, authentication);

        verify(operation).cancelBy(account);
        verify(operationRepository).save(operation);
    }

    @Test
    void test_transactAnOperationByLoggedInAccount() {
        when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        when(operation.transact(any(), any())).thenReturn(transaction);
        when(transactionRepository.save(any())).thenReturn(transaction);

        operationService.transact(operationId, authentication);

        verify(operation).transact(any(), any());
        verify(operationRepository).save(operation);
    }

    @Test
    void test_getActiveOperationsByInvestor() {
        when(operation.getId()).thenReturn(1L);
        when(operation.getCryptoQuotation()).thenReturn(new CryptoQuotation(CryptoCurrency.BNBUSDT, 123d, 123d, LocalDateTime.now()));
        when(operation.getSourceOfOrigin()).thenReturn(marketOrder);
        when(operation.getParty()).thenReturn(marketOrder.getEmitter());
        when(operation.getCounterparty()).thenReturn(marketOrder.getEmitter());
        when(operation.getStatus()).thenReturn(OperationStatus.inProgress());
        when(operation.isActive()).thenReturn(true);

        when(operationRepository.findActiveOperationsByAccountId(account.getId())).thenReturn(List.of(operation));

        var result = operationService.getActiveOperationsFrom(authentication);

        Assertions.assertThat(result).isNotEmpty();
    }

}
