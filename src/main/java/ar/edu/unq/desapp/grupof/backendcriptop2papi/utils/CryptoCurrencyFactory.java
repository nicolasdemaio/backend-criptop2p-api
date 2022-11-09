package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidCryptoCurrencyException;

public class CryptoCurrencyFactory {
    public static CryptoCurrency getCryptoCurrencyFor(String aCryptoCurrency) {
        try {
            return CryptoCurrency.valueOf(aCryptoCurrency);
        } catch (IllegalArgumentException exception) {
            throw new InvalidCryptoCurrencyException();
        }
    }
}
