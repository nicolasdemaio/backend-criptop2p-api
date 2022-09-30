package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApiMessageTest {

    @Test
    void testApiMessage() {
        ResponseEntity<Map<String, Object>> message = new ApiMessage().response(HttpStatus.OK, "Message");

        assertThat(message.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message.getBody()).containsEntry("message","Message");
        assertThat(message.getBody().get("timestamp")).isNotNull();
        assertThat(message.getBody()).containsEntry("status","200 OK");
    }

}
