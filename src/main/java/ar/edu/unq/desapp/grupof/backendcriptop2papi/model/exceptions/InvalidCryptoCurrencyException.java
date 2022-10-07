package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;

public class InvalidCryptoCurrencyException extends RuntimeException {

    public InvalidCryptoCurrencyException() {
        super("Invalid crypto currency symbol, it must be any of these: " + CryptoCurrency.symbols());
    }

}
