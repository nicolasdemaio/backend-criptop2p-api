package ar.edu.unq.desapp.grupof.backendcriptop2papi.resources;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestmentAccountRepository;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InvestorDataLoader {
    @Autowired
    InvestorRepository investorRepository;
    @Autowired
    InvestmentAccountRepository investmentAccountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void loadAnInvestorWithEmailAndPassword(String email, String password){
        Investor investor = InvestorTestResource.anyInvestor();
        investor.setEmail(email);
        investor.setPassword(passwordEncoder.encode(password));
        investorRepository.save(investor);
        investmentAccountRepository.save(new InvestmentAccount(investor));
    }

    public InvestmentAccount loadAnyInvestorAndGetAccount(){
        Investor investor = InvestorTestResource.anyInvestor();
        investorRepository.save(investor);
        return investmentAccountRepository.save(new InvestmentAccount(investor));
    }
}
