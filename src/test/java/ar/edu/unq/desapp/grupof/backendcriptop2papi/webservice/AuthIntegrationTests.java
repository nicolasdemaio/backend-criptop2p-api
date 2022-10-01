package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserLoginRequest;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.resources.InvestorDataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectWriter writer;

    @Autowired
    private InvestorDataLoader investorLoader;

    @BeforeEach
    void setUp() {
        writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
    }

    @Test
    void testRegisterNewUser() throws Exception {
        UserRegistrationForm registrationForm = new UserRegistrationForm("nicolas", "de maio", "nico@gmail.com", "Bernal 1876", "Ndemaio123", "1234567891234567891234", "12345678");
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
    void testBadRequestWhenRegisterUserWithUsedEmail() throws Exception {
        UserRegistrationForm registrationForm = new UserRegistrationForm("nicolas", "de maio", "trejoA@gmail.com", "Bernal 1876", "Ndemaio123", "1234567891234567891234", "12345678");
        String jsonForm = writer.writeValueAsString(registrationForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidUserInRegisterThrowsBadRequest() throws Exception {
        UserRegistrationForm registrationForm = new UserRegistrationForm("nicolas", "de maio", "nico@gmail.com", "Bernal 1876", "Ndemaio123", "12345678912345678912", "12345678");
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
    void testLoginUser() throws Exception {
        String email = "nicoo@gmail.com";
        String password = "DeMaio10@";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, password);
        UserLoginRequest loginRequest = new UserLoginRequest(email, password);
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
    void testLoginInvalidUser() throws Exception {
        String email = "nicoo@gmail.com";
        String password = "DeMaio11@";
        UserLoginRequest loginRequest = new UserLoginRequest(email, password);
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
    void testLoginUserWithWrongPassword() throws Exception {
        String email = "nicooasd@gmail.com";
        String password = "DeMaio11@";
        String wrongPassword = "DeMaio11@asdsa";
        investorLoader.loadAnInvestorWithEmailAndPassword(email, password);
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

}
