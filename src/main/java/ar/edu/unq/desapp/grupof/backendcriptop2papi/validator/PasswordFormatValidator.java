package ar.edu.unq.desapp.grupof.backendcriptop2papi.validator;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidObjectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordFormatValidator {

    private static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\-]]).{6,}$";
    private static final String ERROR_MESSAGE = "Password must contain 1 lowercase, 1 uppercase 1 special character and " +
            "at " +
            "least 6 characters.";

    public void validate(String aPassword) {
        Pattern pattern = java.util.regex.Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(aPassword);

        if (!matcher.matches())
            throw new InvalidObjectException("password", ERROR_MESSAGE);
    }
}