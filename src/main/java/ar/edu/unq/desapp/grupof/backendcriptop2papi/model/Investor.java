package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Entity
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String password;
    private String mercadoPagoCVU;
    private String cryptoWalletAddress;

    public Investor(String name, String surname, String email, String address, String password, String mercadoPagoCVU, String cryptoWalletAddress) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.mercadoPagoCVU = mercadoPagoCVU;
        this.cryptoWalletAddress = cryptoWalletAddress;
    }
}
