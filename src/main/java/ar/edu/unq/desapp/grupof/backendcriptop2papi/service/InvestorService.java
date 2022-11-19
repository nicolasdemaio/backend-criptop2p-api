package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.EmailAlreadyInUseException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvestorNotFoundException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.validator.PasswordFormatValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;
    private final InvestmentAccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InvestorService(InvestorRepository investorRepository, ModelMapper modelMapper,
                           InvestmentAccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.investorRepository = investorRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegistrationForm form){
        validateThatEmailIsNotInUse(form.getEmail());
        new PasswordFormatValidator().validate(form.getPassword());
        Investor newInvestor = form.toModelUsing(passwordEncoder);
        InvestmentAccount newAccount = new InvestmentAccount(newInvestor);

        investorRepository.save(newInvestor);
        accountRepository.save(newAccount);
    }

    public InvestorDTO loginUserWith(UserLoginRequest aRequest) {
        Investor user = getInvestorByEmail(aRequest.getEmail());

        verifyThatPasswordMatches(aRequest, user);
        return modelMapper.map(user, InvestorDTO.class);
    }

    public InvestorDTO authenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        Investor investor = getInvestorByEmail(email);
        return modelMapper.map(investor, InvestorDTO.class);
    }

    private void validateThatEmailIsNotInUse(String anEmail) {
        if (investorRepository.existsInvestorByEmail(anEmail)) throw new EmailAlreadyInUseException();
    }

    private void verifyThatPasswordMatches(UserLoginRequest aRequest, Investor user) {
        if(!passwordEncoder.matches(aRequest.getPassword(), user.getPassword())) throw new InvestorNotFoundException();
    }

    private Investor getInvestorByEmail(String anEmail) {
        return investorRepository
                .findInvestorByEmail(anEmail)
                .orElseThrow(InvestorNotFoundException::new);
    }
}
