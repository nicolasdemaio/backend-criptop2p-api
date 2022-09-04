package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigInteger;

@Data
public class UserRegistrationForm {
    @Size(min=3,max=30)
    private String name;
    @Size(min=3,max=30)
    private String surname;
    @Email
    private String email;
    @Size(min=10,max=30)
    private String address;
    //@Pattern(regexp=)
    private String password;
    @Size(min=22,max=22) // Agregar regex para que sean numericos
    private String mercadoPagoCVU;
    @Size(min=8,max=8) // Agregar regex para que sean numericos
    private String cryptoWalletAddress;

}
