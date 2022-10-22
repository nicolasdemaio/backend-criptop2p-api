package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import java.util.Arrays;
import java.util.List;

public enum CryptoCurrency {
    ALICEUSDT,
    MATICUSDT,
    AXSUSDT,
    AAVEUSDT,
    ATOMUSDT,
    NEOUSDT,
    DOTUSDT,
    ETHUSDT,
    CAKEUSDT,
    BTCUSDT,
    BNBUSDT,
    ADAUSDT,
    TRXUSDT,
    AUDIOUSDT;

    public static List<String> symbols() {
        return Arrays.stream(values()).map(Enum::name).toList();
    }
}
