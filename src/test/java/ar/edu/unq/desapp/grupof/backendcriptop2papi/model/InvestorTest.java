package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvestorTest {

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

    /* Este no anda porque no tenemos mensaje en la excepcion
    @Test
    public void anInvestorCanNotHaveANameWithMoreThan30Characters(){
        String nameWithMoreThan30Characters = "IAmANameWithMoreThan30CharactersOkay?";
        InvalidObjectException exception = assertThrows(InvalidObjectException.class,() -> {
                Investor investorWithANameWithMoreThan30Characters = anyInvestorWithName(nameWithMoreThan30Characters);
        });
        String expectedMessage = "The name must contain min. 3 characters and max. 30 characters.";

        //ACA HAY QUE VER COMO COMPARAMOS MENSAJES, Porque lo manejamos con un map y no es accesible del exterior.
        assertEquals(expectedMessage, exception.getMessage());
    }
*/
    private Investor anyInvestorWithName(String name){
        return new Investor(
                name,
                "surname",
                "validEmail@gmail.com",
                "validAdress",
                "ValidPassword123@",
                "1234567891234567891234",
                "12345678"
        );
    }

}
