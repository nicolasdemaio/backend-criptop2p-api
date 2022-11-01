package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.TradeStatisticsDataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import java.text.MessageFormat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StatisticsControllerTest {

    public static final String VALID_EMAIL = "nicooo@gmail.com";
    private static final String VALID_PASSWORD = "V@lidd123password";

    @Autowired
    private InvestorDataLoader investorLoader;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TradeStatisticsDataLoader tradeStatisticsDataLoader;

    @DisplayName ("Get statistics with transactions test")
    @Test
    void getStatisticsWithTransactionsTest() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "01/01/2022");
        requestParams.add("to", "01/01/2023");

        tradeStatisticsDataLoader.prepareStatistics();

        Long investorId = 1L;
        String getUrl = MessageFormat.format("/api/stats/{0}", investorId);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(getUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(requestParams)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
