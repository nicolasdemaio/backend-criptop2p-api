package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.InvestorInformationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.AccountService;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.InvestorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/users")
public class AuthController {

    private final InvestorService investorService;
    private final JWTTokenManager jwtTokenManager;
    private final AccountService accountService;

    @Autowired
    public AuthController(InvestorService investorService, JWTTokenManager aJwtTokenManager, AccountService accountService) {
        this.investorService = investorService;
        jwtTokenManager = aJwtTokenManager;
        this.accountService = accountService;
    }

    @Operation (summary = "Register an User")
    @PostMapping (path = "/register")
    public ResponseEntity<Map.Entry<String, Object>> registerUser(@RequestBody UserRegistrationForm form){
        investorService.registerUser(form);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.entry("message", "User successfully created"));
    }

    @Operation (summary = "Login with email and password")
    @PostMapping (path = "/login")
    public ResponseEntity<InvestorDTO> loginUserWith(@RequestBody UserLoginRequest aRequest) {
        InvestorDTO loggedUser = investorService.loginUserWith(aRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", jwtTokenManager.generateTokenBasedOn(aRequest.getEmail()))
                .body(loggedUser);
    }

    @Operation (summary = "Get the list of registered Users")
    @GetMapping()
    public ResponseEntity<List<InvestorInformationDTO>> getRegisteredUsers() {
        return ResponseEntity.ok(accountService.getInvestors());
    }

}
