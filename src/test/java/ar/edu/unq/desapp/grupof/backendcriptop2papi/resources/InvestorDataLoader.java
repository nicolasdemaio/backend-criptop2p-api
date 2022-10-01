package ar.edu.unq.desapp.grupof.backendcriptop2papi.resources;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InvestorDataLoader {
    @Autowired
    InvestorRepository investorRepository;
    public void loadAnInvestorWithEmailAndPassword(String email, String password){
        Investor investor = InvestorTestResource.anyInvestor();
        investor.setEmail(email);
        investor.setPassword(password);
        investorRepository.save(investor);
    }
}
