package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderIntegrationTests {

    public static final String VALID_EMAIL = "nicoo@gmail.com";
    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_PASSWORD = "V@lid123password";

    @Autowired
    private InvestorDataLoader investorLoader;

    @Test
    @DisplayName("When try to get active orders without authentication, throws 403 error")
    void testGetActiveOrderThrows403Forbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("When get active orders, but there are not registered orders, it returns an empty list")
    void testGetActiveOrderReturnsEmptyList() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(0)));
    }

}

