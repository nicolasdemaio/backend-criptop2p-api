package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService {

    private OperationRepository operationRepository;
    private InvestorService investorService;
    private InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository, InvestorService investorService, InvestmentAccountRepository investmentAccountRepository) {
        this.operationRepository = operationRepository;
        this.investorService = investorService;
        this.investmentAccountRepository = investmentAccountRepository;
    }

    @Transactional
    public List<OperationDTO> getActiveOperationsFrom(Authentication anAuthentication) {
        InvestorDTO loggedInvestor = investorService.authenticatedUser(anAuthentication);
        InvestmentAccount investmentAccount = investmentAccountRepository.findInvestmentAccountByInvestor(loggedInvestor.getId());

        List<Operation> operations = operationRepository.findActiveOperationsByAccountId(investmentAccount.getId());

        operations = operations.stream().filter(operation -> operation.isActive()).collect(Collectors.toList());

        return operations.stream().map(operation -> OperationDTO.fromModel(operation)).toList();
    }
}
