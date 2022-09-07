package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationForm {
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private String mercadoPagoCVU;
    private String cryptoWalletAddress;

}
