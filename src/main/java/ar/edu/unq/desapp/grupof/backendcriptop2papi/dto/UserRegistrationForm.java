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
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\-]]).{6,}$", message="Must contain 1 lowercase, 1 uppercase 1 special character and at least 6 characters")
    private String password;
    @Pattern(regexp= "^\\d{22}$", message="Must enter 22 digits")
    private String mercadoPagoCVU;
    @Pattern(regexp= "^\\d{8}$", message="Must enter 8 digits")
    private String cryptoWalletAddress;

}
