package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource.*;
import static org.junit.jupiter.api.Assertions.*;

class InvestorTest {
    @Test
    void anInvestorCanHaveANameWithALengthBetween3And30Characters() {
        String nameBetween3And30Characters = "Jhon";
        Investor investorWithNameBetween3And30Characters = anyInvestorWithName(nameBetween3And30Characters);

        assertEquals(nameBetween3And30Characters, investorWithNameBetween3And30Characters.getName());
    }

    @Test
    void anInvestorCanHaveANameWith3Characters() {
        String nameWith3Characters = "Bob";
        Investor investorWithNameWith3Characters = anyInvestorWithName(nameWith3Characters);

        assertEquals(nameWith3Characters, investorWithNameWith3Characters.getName());
    }

    @Test
    void anInvestorCanHaveANameWith30Characters() {
        String nameWith30Characters = "IAmANameWith30CharactersOkay??";
        Investor investorWithNameWith30Characters = anyInvestorWithName(nameWith30Characters);

        assertEquals(nameWith30Characters, investorWithNameWith30Characters.getName());
    }

    @Test
    void anInvestorCanNotHaveANameWithMoreThan30Characters() {
        String nameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithANameWithMoreThan30Characters = anyInvestorWithName(nameWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception, "name");
    }

    @Test
    void anInvestorCanNotHaveANameWithLessThan3Characters() {
        String nameWithLessThan3Characters = "NO";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithANameWithLessThan3Characters = anyInvestorWithName(nameWithLessThan3Characters);
        });
        assertBrokenConstraintWith(exception, "name");
    }

    @Test
    void anInvestorCanHaveASurnameWithALengthBetween3And30Characters() {
        String surnameBetween3And30Characters = "Jhon";
        Investor investorWithSurnameBetween3And30Characters = anyInvestorWithSurname(surnameBetween3And30Characters);

        assertEquals(surnameBetween3And30Characters, investorWithSurnameBetween3And30Characters.getSurname());
    }

    @Test
    void anInvestorCanHaveASurnameWith3Characters() {
        String surnameWith3Characters = "Bob";
        Investor investorWithSurnameWith3Characters = anyInvestorWithSurname(surnameWith3Characters);

        assertEquals(surnameWith3Characters, investorWithSurnameWith3Characters.getSurname());
    }

    @Test
    void anInvestorCanHaveASurnameWith30Characters() {
        String surnameWith30Characters = "IAmANameWith30CharactersOkay??";
        Investor investorWithSurnameWith30Characters = anyInvestorWithSurname(surnameWith30Characters);

        assertEquals(surnameWith30Characters, investorWithSurnameWith30Characters.getSurname());
    }

    @Test
    void anInvestorCanNotHaveASurnameWithMoreThan30Characters() {
        String surnameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithASurnameWithMoreThan30Characters = anyInvestorWithSurname(surnameWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception, "surname");
    }

    @Test
    void anInvestorCanNotHaveASurnameWithLessThan3Characters() {
        String surnameWithLessThan3Characters = "NO";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithASurnameWithLessThan3Characters = anyInvestorWithSurname(surnameWithLessThan3Characters);
        });
        assertBrokenConstraintWith(exception, "surname");
    }

    @Test
    void anInvestorShouldHaveAValidEmail() {
        String validEmail = "Jhon98@gmail.com";
        Investor investorWithValidEmail = anyInvestorWithEmail(validEmail);

        assertEquals(validEmail, investorWithValidEmail.getEmail());
    }

    @Test
    void anInvestorCanNotHaveAnInvalidEmail() {
        String invalidEmail = "IAmAnInvalidEmail";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithInvalidEmail = anyInvestorWithEmail(invalidEmail);
        });
        assertBrokenConstraintWith(exception, "email");
    }

    @Test
    void anInvestorCanHaveAnAddressWithALengthBetween10And30Characters() {
        String addressBetween10And30Characters = "Don Bosco 1998";
        Investor investorWithAddressBetween10And30Characters = anyInvestorWithAddress(addressBetween10And30Characters);

        assertEquals(addressBetween10And30Characters, investorWithAddressBetween10And30Characters.getAddress());
    }

    @Test
    void anInvestorCanHaveAnAddressWith10Characters() {
        String addressWith10Characters = "TenCharact";
        Investor investorWithAddressWith10Characters = anyInvestorWithAddress(addressWith10Characters);

        assertEquals(addressWith10Characters, investorWithAddressWith10Characters.getAddress());
    }

    @Test
    void anInvestorCanHaveAnAddressWith30Characters() {
        String addressWith30Characters = "IAmAAddrWith30CharactersOkay??";
        Investor investorWithAddressWith30Characters = anyInvestorWithAddress(addressWith30Characters);

        assertEquals(addressWith30Characters, investorWithAddressWith30Characters.getAddress());
    }

    @Test
    void anInvestorCanNotHaveAnAddressWithMoreThan30Characters() {
        String addressWithMoreThan30Characters = "IAmAAddressWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAnAddressWithMoreThan30Characters = anyInvestorWithAddress(addressWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception, "address");
    }

    @Test
    void anInvestorCanNotHaveAnAddressWithLessThan10Characters() {
        String addressWithLessThan10Characters = "NotEnough";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAnAddressWithLessThan10Characters = anyInvestorWithAddress(addressWithLessThan10Characters);
        });
        assertBrokenConstraintWith(exception, "address");
    }

    @Test
    void anInvestorCanNotHaveAPasswordWithLessThanSixCharacters() {
        String passwordWithLessThanSixCharac = "Less";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAPassWithLessThan6Characters = anyInvestorWithPassword(passwordWithLessThanSixCharac);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void anInvestorCanNotHaveAPasswordWithNoCapitalLetter() {
        String passwordWithoutCapital = "ihavenocapital";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAPassWithNoCap = anyInvestorWithPassword(passwordWithoutCapital);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void anInvestorCanNotHaveAPasswordWithNoLowerCaseLetter() {
        String passwordWithoutLowerCase = "IHAVENOLOWER";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAPassWithNoLowerCase = anyInvestorWithPassword(passwordWithoutLowerCase);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void anInvestorCanNotHaveAPasswordWithNoSpecialCharacter() {
        String passwordWithoutSpecialChar = "IHaveNoSpecialChar";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithAPassWithNoSpecialChar = anyInvestorWithPassword(passwordWithoutSpecialChar);
        });
        assertBrokenConstraintWith(exception, "password");
    }

    @Test
    void anInvestorShouldHaveAPasswordWithAtLeastSixCharactersOneLowerAndUpperCaseAndASpecialCharacter() {
        String validPassword = "ValidPassword@";
        Investor investorWithValidPass = anyInvestorWithPassword(validPassword);

        assertEquals(validPassword, investorWithValidPass.getPassword());
    }

    @Test
    void anInvestorShouldHaveAMercadoPagoCVUWith22Characters() {
        String validCVU = "1234567890123456789012";
        Investor investorWithValidCVU = anyInvestorWithMercadoPagoCVU(validCVU);

        assertEquals(validCVU, investorWithValidCVU.getMercadoPagoCVU());
    }

    @Test
    void anInvestorCanNotHaveAnInvalidCVU() {
        String invalidCVU = "1234";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithInvalidCVU = anyInvestorWithMercadoPagoCVU(invalidCVU);
        });
        assertBrokenConstraintWith(exception, "mercadoPagoCVU");
    }

    @Test
    void anInvestorShouldHaveACryptoWalletAddressWith8Characters() {
        String validCryptoWalletAddress = "12345678";
        Investor investorWithValidCryptoAddr = anyInvestorWithWalletAddress(validCryptoWalletAddress);

        assertEquals(validCryptoWalletAddress, investorWithValidCryptoAddr.getCryptoWalletAddress());
    }

    @Test
    void anInvestorCanNotHaveAnInvalidCryptoWalletAddress() {
        String invalidCryptoWalletAddress = "1234";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> {
            Investor investorWithInvalidWalletAddress = anyInvestorWithWalletAddress(invalidCryptoWalletAddress);
        });
        assertBrokenConstraintWith(exception, "cryptoWalletAddress");
    }

    @Test
    void anInvestorCannotBeCreatedWithNullFields() {
        Assertions.assertThatThrownBy(() ->
                new Investor(
                        null,
                        "Trejo",
                        "trejo@gmail.com",
                        "Fake 123",
                        "p4ssw0rd",
                        "1234567891234567891234",
                        "12345678"
                )).isInstanceOf(InvalidObjectException.class);
    }

    private void assertBrokenConstraintWith(InvalidObjectException exception, String attribute) {
        assertTrue(exception.getBrokenConstraints().containsKey(attribute));
    }

}
