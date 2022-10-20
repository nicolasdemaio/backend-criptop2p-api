package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorInformationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private InvestmentAccountRepository investmentAccountRepository;

    @Autowired
    public AccountService(InvestmentAccountRepository investmentAccountRepository) {
        this.investmentAccountRepository = investmentAccountRepository;
    }

    public List<InvestorInformationDTO> getInvestors() {
        var accounts = investmentAccountRepository.findAll();
        return accounts.stream().map(account -> InvestorInformationDTO.fromModel(account)).toList();
    }
}
