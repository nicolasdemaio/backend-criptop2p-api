package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Entity
public class Investor extends ValidatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min=3,max=30, message = "The name must contain min. 3 characters and max. 30 characters.")
    private String name;
    @Size(min=3,max=30, message = "The surname must contain min. 3 characters and max. 30 characters.")
    private String surname;
    @Email(message = "The email format is not valid.")
    private String email;
    @Size(min=10,max=30, message = "The address must contain min. 10 characters and max. 30 characters.")
    private String address;
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\-]]).{6,}$", message="Password must contain 1 lowercase, 1 uppercase 1 special character and at least 6 characters.")
    private String password;
    @Pattern(regexp= "^\\d{22}$", message="Mercado Pago CVU size must be equals to 22 digits.")
    private String mercadoPagoCVU;
    @Pattern(regexp= "^\\d{8}$", message="Crypto Wallet Address size must be equals to 8 digits.")
    private String cryptoWalletAddress;

    protected Investor(){ /*Required by ORM framework.*/ }

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

