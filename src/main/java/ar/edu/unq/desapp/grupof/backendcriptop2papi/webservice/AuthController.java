package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.InvestorService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(path = "/api/users")
public class AuthController {

    private final InvestorService investorService;
    private final JWTTokenManager jwtTokenManager;

    @Autowired
    public AuthController(InvestorService investorService, JWTTokenManager aJwtTokenManager) {
        this.investorService = investorService;
        jwtTokenManager = aJwtTokenManager;
    }

    @PostMapping (path = "/register")
    public ResponseEntity<Map.Entry<String, Object>> registerUser(@RequestBody UserRegistrationForm form){
        investorService.registerUser(form);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.entry("message", "User successfully created"));
    }

    @PostMapping (path = "/login")
    public ResponseEntity<?> loginUserWith(@RequestBody UserLoginRequest aRequest) {
        InvestorDTO loggedUser = investorService.loginUserWith(aRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", jwtTokenManager.generateTokenBasedOn(aRequest.getEmail()))
                .body(loggedUser);
    }

    @GetMapping (path = "/current")
    @Authorization(value = "Authorization")
    public ResponseEntity<?> authenticatedUser() {
        InvestorDTO authenticatedUser = investorService.authenticatedUser();
        return ResponseEntity.status(HttpStatus.OK).body(authenticatedUser);
    }

}
