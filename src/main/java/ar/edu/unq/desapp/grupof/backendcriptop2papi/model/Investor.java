package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class Investor extends ValidatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public Investor(String name, String surname, String email, String address, String password, String mercadoPagoCVU, String cryptoWalletAddress) throws InvalidObjectException {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.cryptoWalletAddress = cryptoWalletAddress;
        validate();
    }
}

