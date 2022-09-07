package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.InvestorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    InvestorRepository investorRepository;
    ModelMapper modelMapper;

    @Autowired
    public UserService(InvestorRepository investorRepository, ModelMapper modelMapper){
        this.investorRepository = investorRepository;
        this.modelMapper = modelMapper;
    }

    public void registerUser(UserRegistrationForm form){
        Investor newInvestor = modelMapper.map(form,Investor.class);
        investorRepository.save(newInvestor);
    }

}
