package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.UserRegistrationForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectWriter writer;

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
                         .post("/api/users")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(jsonForm))
                 .andDo(print())
                 .andExpect(status().isCreated());
    }

    @Test
    void testInvalidUserThrowsBadRequest() throws Exception {
        UserRegistrationForm registrationForm = new UserRegistrationForm("nicolas", "de maio", "nico@gmail.com", "Bernal 1876", "Ndemaio123", "12345678912345678912", "12345678");
        String jsonForm = writer.writeValueAsString(registrationForm);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

}
