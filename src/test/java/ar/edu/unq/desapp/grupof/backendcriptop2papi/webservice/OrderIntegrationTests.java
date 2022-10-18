package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OrderForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.SalesOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.MarketOrderDataLoader;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTests {

    public static final String VALID_EMAIL = "nicoo@gmail.com";
    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_PASSWORD = "V@lid123password";

    @Autowired
    private InvestorDataLoader investorLoader;

    @Autowired
    private MarketOrderDataLoader marketOrderLoader;

    @Autowired
    QuotationService quotationService;

    private ObjectWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
    }

    @Test
    @DisplayName("When try to get active orders without authentication, throws 403 error")
    void testGetActiveOrderThrows403Forbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("When get active orders, it returns orders that are not taken and 200 OK")
    void testGetActiveOrders() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test create a purchase market order")
    void testCreateAPurchaseOrder() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        OrderForm orderForm = anyPurchaseOrderForm();
        String jsonForm = writer.writeValueAsString(orderForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test create a sales market order")
    void testCreateASalesOrder() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        OrderForm orderForm = anySalesOrderForm();
        String jsonForm = writer.writeValueAsString(orderForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test invalid order type exception")
    void testInvalidOrderTypeException() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        OrderForm orderForm = anySalesOrderForm();
        orderForm.setOperationType("INVALID");
        String jsonForm = writer.writeValueAsString(orderForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test Apply for an order")
    void testApplyForOrder() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        Long orderId = marketOrderLoader.loadAnyMarketOrderWithType(new SalesOrder()).getId();
        String postUrl = MessageFormat.format("/api/orders/{0}", orderId);


        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test Apply for an order that does not exist")
    void testApplyForOrderThatDoesNotExist() throws Exception {
        investorLoader.loadAnInvestorWithEmailAndPassword(VALID_EMAIL, VALID_PASSWORD);
        String token = new JWTTokenManager().generateTokenBasedOn(VALID_EMAIL);

        Long invalidId = 250l;
        String postUrl = MessageFormat.format("/api/orders/{0}", invalidId);


        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private OrderForm anyPurchaseOrderForm(){
        Double validPrice = quotationService.getCryptoQuotation(CryptoCurrency.AAVEUSDT).getPriceInPesos();
        return new OrderForm(CryptoCurrency.AAVEUSDT.name(),1d,validPrice,"PURCHASE");
    }

    private OrderForm anySalesOrderForm(){
        Double validPrice = quotationService.getCryptoQuotation(CryptoCurrency.AAVEUSDT).getPriceInPesos();
        return new OrderForm(CryptoCurrency.AAVEUSDT.name(),1d,validPrice,"SALES");
    }
}

