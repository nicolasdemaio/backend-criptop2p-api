package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;


import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuotationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InvestorDataLoader investorLoader;

    private static final String VALID_PASSWORD = "V@lid123password";
    private static final Integer NUMBER_OF_QUOTATIONS = 14;

    @Test
    void getAllQuotationsTest() throws Exception {
        String email = "nicoauth@gmail.com";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, VALID_PASSWORD);

        String token = new JWTTokenManager().generateTokenBasedOn(email);

        MockHttpServletResponse response =
                mockMvc
                        .perform(MockMvcRequestBuilders
                                .get("/api/quotations")
                                .header("Authorization", token))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        String json = response.getContentAsString();
        var quotations = new ObjectMapper().readValue(json, List.class);

        Assertions.assertThat(quotations).hasSize(NUMBER_OF_QUOTATIONS);
    }
}
