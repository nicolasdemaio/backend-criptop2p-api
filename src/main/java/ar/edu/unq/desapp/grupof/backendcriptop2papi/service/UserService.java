package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.EmailAlreadyInUseException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final InvestorRepository investorRepository;
    private final InvestmentAccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(InvestorRepository investorRepository, ModelMapper modelMapper, InvestmentAccountRepository accountRepository){
        this.investorRepository = investorRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    public void registerUser(UserRegistrationForm form){
        validateThatEmailIsNotInUse(form.getEmail());
        Investor newInvestor = modelMapper.map(form,Investor.class);
        InvestmentAccount newAccount = new InvestmentAccount(newInvestor);

        investorRepository.save(newInvestor);
        accountRepository.save(newAccount);
    }

    private void validateThatEmailIsNotInUse(String anEmail) {
        if (investorRepository.existsInvestorByEmail(anEmail)) throw new EmailAlreadyInUseException();
    }

}
