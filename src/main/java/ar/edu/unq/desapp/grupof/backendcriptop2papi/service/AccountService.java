package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorInformationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {

    private InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    public AccountService(InvestmentAccountRepository investmentAccountRepository) {
        this.investmentAccountRepository = investmentAccountRepository;
    }

    @Transactional
    public List<InvestorInformationDTO> getInvestors() {
        var accounts = investmentAccountRepository.findAll();
        return accounts.stream().map(InvestorInformationDTO::fromModel).toList();
    }
}
