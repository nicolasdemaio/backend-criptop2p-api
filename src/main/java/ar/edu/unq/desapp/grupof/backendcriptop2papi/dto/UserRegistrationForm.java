package ar.edu.unq.desapp.grupof.backendcriptop2papi.dto;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
public class UserRegistrationForm {
    private final String name;
    private final String surname;
    private final String email;
    private final String address;
    private final String password;
    @JsonProperty("mercado_pago_cvu")
    private final String mercadoPagoCVU;
    @JsonProperty("crypto_wallet_address")
    private final String cryptoWalletAddress;

    public Investor toModelUsing(PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(password);
        return new Investor(name, surname, email, address, encodedPassword, mercadoPagoCVU, cryptoWalletAddress);
    }
}
