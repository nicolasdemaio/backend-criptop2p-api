package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (path = "/api")
public class AliveController {

    @Operation(summary = "Check if the service is active")
    @GetMapping (path = "/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("Pong");
    }

}
