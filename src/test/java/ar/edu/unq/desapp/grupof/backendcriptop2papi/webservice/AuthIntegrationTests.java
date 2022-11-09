package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.config.JWTTokenManager;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectWriter writer;

    private static final String VALID_PASSWORD = "V@lid1234password";

    @Autowired
    private InvestorDataLoader investorLoader;

    @BeforeEach
    void setUp() {
        writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
    }

    @Test
    @DisplayName("IT can register a new user")
    void testRegisterNewUser() throws Exception {
        UserRegistrationForm registrationForm = registrationFormWithEmailAndPassword("nicoregiister@gmail.com", VALID_PASSWORD);
        String jsonForm = writer.writeValueAsString(registrationForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("IT cannot register an user with an used email")
    void testBadRequestWhenRegisterUserWithUsedEmail() throws Exception {
        String used_email = "trejoA@gmail.com";
        UserRegistrationForm registrationForm = registrationFormWithEmailAndPassword(used_email, VALID_PASSWORD);
        String jsonForm = writer.writeValueAsString(registrationForm);
        investorLoader.loadAnInvestorWithEmailAndPassword(used_email, VALID_PASSWORD);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IT cannot register an user with invalid data: Password does not satisfy constraints")
    void testInvalidUserInRegisterThrowsBadRequest() throws Exception {
        UserRegistrationForm registrationForm = registrationFormWithEmailAndPassword("nico@gmail.com", "123123");
        String jsonForm = writer.writeValueAsString(registrationForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("IT login a registered user, return jwt token")
    void testLoginUser() throws Exception {
        String email = "nicoo@gmail.com";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, VALID_PASSWORD);
        UserLoginRequest loginRequest = new UserLoginRequest(email, VALID_PASSWORD);
        String jsonForm = writer.writeValueAsString(loginRequest);

        MockHttpServletResponse response =
                mockMvc
                        .perform(MockMvcRequestBuilders
                                .post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonForm))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Assertions.assertThat(response.containsHeader("Authorization")).isTrue();
    }

    @Test
    @DisplayName("IT when login a non registered user, throws exception")
    void testLoginInvalidUser() throws Exception {
        String email = "nicononregistered@gmail.com";
        UserLoginRequest loginRequest = new UserLoginRequest(email, VALID_PASSWORD);
        String jsonForm = writer.writeValueAsString(loginRequest);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IT when try to login a registered user but with wrong password, throws exception")
    void testLoginUserWithWrongPassword() throws Exception {
        String email = "nicooasd@gmail.com";
        String wrongPassword = "DeMaio11@asdsa";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, VALID_PASSWORD);
        UserLoginRequest loginRequest = new UserLoginRequest(email, wrongPassword);
        String jsonForm = writer.writeValueAsString(loginRequest);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IT can get authenticated user by JWT token")
    void testGetAuthenticatedUserByToken() throws Exception {
        String email = "authuser@gmail.com";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, VALID_PASSWORD);

        String token = new JWTTokenManager().generateTokenBasedOn(email);

        mockMvc
                        .perform(MockMvcRequestBuilders
                                .get("/api/users")
                                .header("Authorization", token))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    private UserRegistrationForm registrationFormWithEmailAndPassword(String anEmail, String aPassword) {
        return UserRegistrationForm
                .builder()
                .name("testing_user")
                .surname("surname_testing_user")
                .email(anEmail)
                .password(aPassword)
                .address("Bernal 1876")
                .mercadoPagoCVU("1234567891234567891234")
                .cryptoWalletAddress("12345678")
                .build();
    }

}
