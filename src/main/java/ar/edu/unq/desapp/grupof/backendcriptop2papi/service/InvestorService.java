package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.EmailAlreadyInUseException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvestorNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;
    private final InvestmentAccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public InvestorService(InvestorRepository investorRepository, ModelMapper modelMapper, InvestmentAccountRepository accountRepository){
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

    public InvestorDTO loginUserWith(UserLoginRequest aRequest) {
        var exception = new InvestorNotFoundException();
        Investor user = investorRepository.findInvestorByEmail(aRequest.getEmail()).orElseThrow(() -> exception);

        if(!user.getPassword().equals(aRequest.getPassword())) throw exception;
        return modelMapper.map(user, InvestorDTO.class);
    }

    private void validateThatEmailIsNotInUse(String anEmail) {
        if (investorRepository.existsInvestorByEmail(anEmail)) throw new EmailAlreadyInUseException();
    }

    public InvestorDTO authenticatedUser(Authentication authentication) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Investor investor = investorRepository.findInvestorByEmail(email).get();
        return modelMapper.map(investor, InvestorDTO.class);
    }
}
