package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvestorTest {
    private final String ANY_NAME = "anyname";
    private final String ANY_SURNAME = "surname";
    private final String ANY_EMAIL = "validEmail@gmail.com";
    private final String ANY_ADDRESS = "validAdress";
    private final String ANY_PASSWORD = "ValidPassword123@";
    private final String ANY_MERCADO_PAGO_CVU = "1234567891234567891234";
    private final String ANY_WALLET_ADDRESS ="12345678";


    @Test
    public void anInvestorCanHaveANameWithALengthBetween3And30Characters(){
        String nameBetween3And30Characters = "Jhon";
        Investor investorWithNameBetween3And30Characters = anyInvestorWithName(nameBetween3And30Characters);

        assertEquals(nameBetween3And30Characters, investorWithNameBetween3And30Characters.getName());
    }

    @Test
    public void anInvestorCanHaveANameWith3Characters(){
        String nameWith3Characters = "Bob";
        Investor investorWithNameWith3Characters = anyInvestorWithName(nameWith3Characters);

        assertEquals(nameWith3Characters, investorWithNameWith3Characters.getName());
    }

    @Test
    public void anInvestorCanHaveANameWith30Characters(){
        String nameWith30Characters = "IAmANameWith30CharactersOkay??";
        Investor investorWithNameWith30Characters = anyInvestorWithName(nameWith30Characters);

        assertEquals(nameWith30Characters, investorWithNameWith30Characters.getName());
    }

    @Test
    public void anInvestorCanNotHaveANameWithMoreThan30Characters(){
        String nameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
                Investor investorWithANameWithMoreThan30Characters = anyInvestorWithName(nameWithMoreThan30Characters);
        });
        assertBrokenConstraintWith(exception,"name");
    }

    @Test
    public void anInvestorCanNotHaveANameWithLessThan3Characters(){
        String nameWithLessThan3Characters = "NO";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
            Investor investorWithANameWithLessThan3Characters = anyInvestorWithName(nameWithLessThan3Characters);
        });
        assertBrokenConstraintWith(exception,"name");
    }

    private void assertBrokenConstraintWith(InvalidObjectException exception, String attribute) {
        assertTrue(exception.getBrokenConstraints().containsKey(attribute));
    }

    private Investor anyInvestor(){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }
    private Investor anyInvestorWithName(String name){
        return new Investor(
                name,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithSurname(String surname){
        return new Investor(
                ANY_NAME,
                surname,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithEmail(String email){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                email,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithAddress(String address){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                address,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithPassword(String password){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                password,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithMercadoPagoCVU(String cvu){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                cvu,
                ANY_WALLET_ADDRESS
        );
    }

    private Investor anyInvestorWithAnyWalletAddress(String walletAddress){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                walletAddress
        );
    }

}
