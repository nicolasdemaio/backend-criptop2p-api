package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ContextService {

    private InvestmentAccountRepository investmentAccountRepository;
    private InvestorService investorService;

    @Autowired
    public ContextService(InvestmentAccountRepository investmentAccountRepository, InvestorService investorService) {
        this.investmentAccountRepository = investmentAccountRepository;
        this.investorService = investorService;
    }

    public InvestmentAccount getCurrentAccount(Authentication authentication) {
        InvestorDTO loggedInvestor = investorService.authenticatedUser(authentication);
        return investmentAccountRepository.findInvestmentAccountByInvestor(loggedInvestor.getId());
    }

}
