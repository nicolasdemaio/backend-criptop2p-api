package ar.edu.unq.desapp.grupof.backendcriptop2papi.controller;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.TransactionDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OperationService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice.OperationController;
import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OperationControllerTest {
    @Mock
    private OperationService operationService;

    @InjectMocks
    private OperationController operationController;

    private Authentication authentication = Mockito.mock(Authentication.class);
    private SecurityContext securityContext = Mockito.mock(org.springframework.security.core.context.SecurityContext.class);

    @BeforeEach
    void setUp(){
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void getActiveOperationsReturnsStatusCodeOKTest(){
        List<OperationDTO> returnedList = Collections.emptyList();
        Mockito.when(operationService.getActiveOperationsFrom(authentication)).thenReturn(returnedList);

        ResponseEntity<List<OperationDTO>> response = operationController.getActiveOperations();

        assertIsOkStatus(response.getStatusCode());
    }

    @Test
    void transactReturnsStatusCodeOKTest(){
        Long investorId = 1L;
        TransactionDTO transaction = Mockito.mock(TransactionDTO.class);
        Mockito.when(operationService.transact(investorId,authentication)).thenReturn(transaction);

        ResponseEntity<TransactionDTO> response = operationController.transact(investorId);

        assertIsOkStatus(response.getStatusCode());
    }

    @Test
    void cancelReturnsStatusCodeOKTest(){
        Long investorId = 1L;
        TransactionDTO transaction = Mockito.mock(TransactionDTO.class);
        Mockito.when(operationService.cancelOperationById(investorId,authentication)).thenReturn(transaction);

        ResponseEntity<TransactionDTO> response = operationController.cancelOperation(investorId);

        assertIsOkStatus(response.getStatusCode());
    }

    private AbstractComparableAssert<?, HttpStatus> assertIsOkStatus(HttpStatus response) {
        return Assertions.assertThat(response).isEqualTo(HttpStatus.OK);
    }


}
