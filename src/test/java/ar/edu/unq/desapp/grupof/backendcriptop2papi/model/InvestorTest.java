package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;


import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorTestResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvestorTest {
    @Test
    public void anInvestorCanHaveANameWithALengthBetween3And30Characters(){
        String nameBetween3And30Characters = "Jhon";
        Investor investorWithNameBetween3And30Characters = InvestorTestResource.anyInvestorWithName(nameBetween3And30Characters);

        assertEquals(nameBetween3And30Characters, investorWithNameBetween3And30Characters.getName());
    }

    @Test
    public void anInvestorCanHaveANameWith3Characters(){
        String nameWith3Characters = "Bob";
        Investor investorWithNameWith3Characters = InvestorTestResource.anyInvestorWithName(nameWith3Characters);

        assertEquals(nameWith3Characters, investorWithNameWith3Characters.getName());
    }

    @Test
    public void anInvestorCanHaveANameWith30Characters(){
        String nameWith30Characters = "IAmANameWith30CharactersOkay??";
        Investor investorWithNameWith30Characters = InvestorTestResource.anyInvestorWithName(nameWith30Characters);

        assertEquals(nameWith30Characters, investorWithNameWith30Characters.getName());
    }

    @Test
    public void anInvestorCanNotHaveANameWithMoreThan30Characters(){
        String nameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
                Investor investorWithANameWithMoreThan30Characters = InvestorTestResource.anyInvestorWithName(nameWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception,"name");
    }

    @Test
    public void anInvestorCanNotHaveANameWithLessThan3Characters(){
        String nameWithLessThan3Characters = "NO";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithANameWithLessThan3Characters = InvestorTestResource.anyInvestorWithName(nameWithLessThan3Characters);
        });
        assertBrokenConstraintWith(exception,"name");
    }

    @Test
    public void anInvestorCanHaveASurnameWithALengthBetween3And30Characters(){
        String surnameBetween3And30Characters = "Jhon";
        Investor investorWithSurnameBetween3And30Characters = InvestorTestResource.anyInvestorWithSurname(surnameBetween3And30Characters);

        assertEquals(surnameBetween3And30Characters, investorWithSurnameBetween3And30Characters.getSurname());
    }

    @Test
    public void anInvestorCanHaveASurnameWith3Characters(){
        String surnameWith3Characters = "Bob";
        Investor investorWithSurnameWith3Characters = InvestorTestResource.anyInvestorWithSurname(surnameWith3Characters);

        assertEquals(surnameWith3Characters, investorWithSurnameWith3Characters.getSurname());
    }

    @Test
    public void anInvestorCanHaveASurnameWith30Characters(){
        String surnameWith30Characters = "IAmANameWith30CharactersOkay??";
        Investor investorWithSurnameWith30Characters = InvestorTestResource.anyInvestorWithSurname(surnameWith30Characters);

        assertEquals(surnameWith30Characters, investorWithSurnameWith30Characters.getSurname());
    }

    @Test
    public void anInvestorCanNotHaveASurnameWithMoreThan30Characters(){
        String surnameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithASurnameWithMoreThan30Characters = InvestorTestResource.anyInvestorWithSurname(surnameWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception,"surname");
    }

    @Test
    public void anInvestorCanNotHaveASurnameWithLessThan3Characters(){
        String surnameWithLessThan3Characters = "NO";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithASurnameWithLessThan3Characters = InvestorTestResource.anyInvestorWithSurname(surnameWithLessThan3Characters);
        });
        assertBrokenConstraintWith(exception,"surname");
    }

    @Test
    public void anInvestorShouldHaveAValidEmail(){
        String validEmail = "Jhon98@gmail.com";
        Investor investorWithValidEmail = InvestorTestResource.anyInvestorWithEmail(validEmail);

        assertEquals(validEmail, investorWithValidEmail.getEmail());
    }

    @Test
    public void anInvestorCanNotHaveAnInvalidEmail(){
        String invalidEmail = "IAmAnInvalidEmail";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithInvalidEmail = InvestorTestResource.anyInvestorWithEmail(invalidEmail);
        });
        assertBrokenConstraintWith(exception,"email");
    }

    @Test
    public void anInvestorCanHaveAnAddressWithALengthBetween10And30Characters(){
        String addressBetween10And30Characters = "Don Bosco 1998";
        Investor investorWithAddressBetween10And30Characters = InvestorTestResource.anyInvestorWithAddress(addressBetween10And30Characters);

        assertEquals(addressBetween10And30Characters, investorWithAddressBetween10And30Characters.getAddress());
    }

    @Test
    public void anInvestorCanHaveAnAddressWith10Characters(){
        String addressWith10Characters = "TenCharact";
        Investor investorWithAddressWith10Characters = InvestorTestResource.anyInvestorWithAddress(addressWith10Characters);

        assertEquals(addressWith10Characters, investorWithAddressWith10Characters.getAddress());
    }

    @Test
    public void anInvestorCanHaveAnAddressWith30Characters(){
        String addressWith30Characters = "IAmAAddrWith30CharactersOkay??";
        Investor investorWithAddressWith30Characters = InvestorTestResource.anyInvestorWithAddress(addressWith30Characters);

        assertEquals(addressWith30Characters, investorWithAddressWith30Characters.getAddress());
    }

    @Test
    public void anInvestorCanNotHaveAnAddressWithMoreThan30Characters(){
        String addressWithMoreThan30Characters = "IAmAAddressWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAnAddressWithMoreThan30Characters = InvestorTestResource.anyInvestorWithAddress(addressWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception,"address");
    }

    @Test
    public void anInvestorCanNotHaveAnAddressWithLessThan10Characters(){
        String addressWithLessThan10Characters = "NotEnough";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAnAddressWithLessThan10Characters = InvestorTestResource.anyInvestorWithAddress(addressWithLessThan10Characters);
        });
        assertBrokenConstraintWith(exception,"address");
    }

    @Test
    public void anInvestorCanNotHaveAPasswordWithLessThanSixCharacters(){
        String passwordWithLessThanSixCharac = "Less";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAPassWithLessThan6Characters = InvestorTestResource.anyInvestorWithPassword(passwordWithLessThanSixCharac);
        });
        assertBrokenConstraintWith(exception,"password");
    }

    @Test
    public void anInvestorCanNotHaveAPasswordWithNoCapitalLetter(){
        String passwordWithoutCapital = "ihavenocapital";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAPassWithNoCap = InvestorTestResource.anyInvestorWithPassword(passwordWithoutCapital);
        });
        assertBrokenConstraintWith(exception,"password");
    }

    @Test
    public void anInvestorCanNotHaveAPasswordWithNoLowerCaseLetter(){
        String passwordWithoutLowerCase = "IHAVENOLOWER";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAPassWithNoLowerCase = InvestorTestResource.anyInvestorWithPassword(passwordWithoutLowerCase);
        });
        assertBrokenConstraintWith(exception,"password");
    }

    @Test
    public void anInvestorCanNotHaveAPasswordWithNoSpecialCharacter(){
        String passwordWithoutSpecialChar = "IHaveNoSpecialChar";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithAPassWithNoSpecialChar = InvestorTestResource.anyInvestorWithPassword(passwordWithoutSpecialChar);
        });
        assertBrokenConstraintWith(exception,"password");
    }

    @Test
    public void anInvestorShouldHaveAPasswordWithAtLeastSixCharactersOneLowerAndUpperCaseAndASpecialCharacter(){
        String validPassword = "ValidPassword@";
        Investor investorWithValidPass = InvestorTestResource.anyInvestorWithPassword(validPassword);

        assertEquals(validPassword, investorWithValidPass.getPassword());
    }

    @Test
    public void anInvestorShouldHaveAMercadoPagoCVUWith22Characters(){
        String validCVU = "1234567890123456789012";
        Investor investorWithValidCVU = InvestorTestResource.anyInvestorWithMercadoPagoCVU(validCVU);

        assertEquals(validCVU, investorWithValidCVU.getMercadoPagoCVU());
    }

    @Test
    public void anInvestorCanNotHaveAnInvalidCVU(){
        String invalidCVU = "1234";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithInvalidCVU = InvestorTestResource.anyInvestorWithMercadoPagoCVU(invalidCVU);
        });
        assertBrokenConstraintWith(exception,"mercadoPagoCVU");
    }

    @Test
    public void anInvestorShouldHaveACryptoWalletAddressWith8Characters(){
        String validCryptoWalletAddress = "12345678";
        Investor investorWithValidCryptoAddr = InvestorTestResource.anyInvestorWithWalletAddress(validCryptoWalletAddress);

        assertEquals(validCryptoWalletAddress, investorWithValidCryptoAddr.getCryptoWalletAddress());
    }

    @Test
    public void anInvestorCanNotHaveAnInvalidCryptoWalletAddress(){
        String invalidCryptoWalletAddress = "1234";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithInvalidWalletAddress = InvestorTestResource.anyInvestorWithWalletAddress(invalidCryptoWalletAddress);
        });
        assertBrokenConstraintWith(exception,"cryptoWalletAddress");
    }

    private void assertBrokenConstraintWith(InvalidObjectException exception, String attribute) {
        assertTrue(exception.getBrokenConstraints().containsKey(attribute));
    }
}
