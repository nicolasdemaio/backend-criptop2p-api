package ar.edu.unq.desapp.grupof.backendcriptop2papi.validator;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordFormatValidatorTest {

    private PasswordFormatValidator passwordFormatValidator;

    @BeforeEach
    void setUp() {
        passwordFormatValidator = new PasswordFormatValidator();
    }

    @Test
    void aPasswordCannotBeCreatedWithLessThanSixCharacters() {
        String passwordWithLessThanSixCharac = "Less";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            passwordFormatValidator.validate(passwordWithLessThanSixCharac);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void aPasswordCannotBeCreatedWithNoCapitalLetter() {
        String passwordWithoutCapital = "ihavenocapital";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            passwordFormatValidator.validate(passwordWithoutCapital);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void aPasswordCannotBeCreatedWithNoLowerCaseLetter() {
        String passwordWithoutLowerCase = "IHAVENOLOWER";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            passwordFormatValidator.validate(passwordWithoutLowerCase);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void aPasswordCannotBeCreatedWithNoSpecialCharacter() {
        String passwordWithoutSpecialChar = "IHaveNoSpecialChar";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            passwordFormatValidator.validate(passwordWithoutSpecialChar);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void aPasswordIsCreatedCorrectly() {
        String validPassword = "Nico123.";
        Assertions.assertDoesNotThrow(() -> passwordFormatValidator.validate(validPassword));
    }

    private void assertBrokenConstraintWith(InvalidObjectException exception, String attribute) {
        assertTrue(exception.getBrokenConstraints().containsKey(attribute));
    }

}