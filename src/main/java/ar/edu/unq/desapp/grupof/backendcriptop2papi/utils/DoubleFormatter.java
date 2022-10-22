package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

public class DoubleFormatter {

    /**
     * It returns the Double value with 2 decimal places.
     * @param aValue - Double to format
     * @return formatted value
     */
    public static Double f(Double aValue) {
        return Math.round(aValue*100.0) / 100.0;
    }

}
