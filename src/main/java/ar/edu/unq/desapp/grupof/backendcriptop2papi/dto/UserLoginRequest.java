package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String email, password;

    public UserLoginRequest(String anEmail, String aPassword) {
       email = anEmail;
       password = aPassword;
    }
}
