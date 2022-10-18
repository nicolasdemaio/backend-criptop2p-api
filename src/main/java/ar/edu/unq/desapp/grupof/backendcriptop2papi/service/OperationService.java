package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.TransactionDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Transaction;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OperationNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService {

    private OperationRepository operationRepository;
    private ContextService contextService;

    @Autowired
    public OperationService(OperationRepository operationRepository, ContextService contextService) {
        this.operationRepository = operationRepository;
        this.contextService = contextService;
    }

    @Transactional
    public List<OperationDTO> getActiveOperationsFrom(Authentication anAuthentication) {
        InvestmentAccount investmentAccount = contextService.getCurrentAccount(anAuthentication);

        List<Operation> operations = operationRepository.findActiveOperationsByAccountId(investmentAccount.getId());

        operations = operations.stream().filter(operation -> operation.isActive()).collect(Collectors.toList());

        return operations.stream().map(operation -> OperationDTO.fromModel(operation)).toList();
    }

    public TransactionDTO transact(Long operationId, Authentication authentication) {
        InvestmentAccount investmentAccount = contextService.getCurrentAccount(authentication);

        Operation operationToBeTransacted = getOperationById(operationId);

        Transaction generatedTransaction = operationToBeTransacted.transact(investmentAccount, LocalDateTime.now());

        operationRepository.save(operationToBeTransacted); //Asumo que se guarda la transaction por cascade

        return TransactionDTO.fromModel(generatedTransaction);
    }

    public TransactionDTO cancelOperationById(Long operationId, Authentication authentication) {
        InvestmentAccount investmentAccount = contextService.getCurrentAccount(authentication);
        Operation operation = getOperationById(operationId);

        Transaction generatedTransaction = operation.cancelBy(investmentAccount);

        operationRepository.save(operation);

        return TransactionDTO.fromModel(generatedTransaction);
    }

    public Operation getOperationById(Long anOperationId) {
        return operationRepository.findById(anOperationId).orElseThrow(() -> new OperationNotFoundException());
    }
}
