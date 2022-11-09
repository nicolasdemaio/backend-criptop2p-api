package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.TransactionDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "/api/operations")
public class OperationController {

    private OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @Operation (summary = "Get a list of all your current active Operations")
    @GetMapping
    public ResponseEntity<List<OperationDTO>> getActiveOperations() {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.getActiveOperationsFrom(SecurityContextHolder.getContext().getAuthentication()));
    }

    @Operation (summary = "Transact with an active Operation you take part in")
    @PutMapping (path = "/{operationId}")
    public ResponseEntity<TransactionDTO> transact(@PathVariable Long operationId) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.transact(operationId, SecurityContextHolder.getContext().getAuthentication()));
    }

    @Operation (summary = "Cancel an active Operation you take part in")
    @PutMapping (path = "/{operationId}/cancel")
    public ResponseEntity<TransactionDTO> cancelOperation(@PathVariable Long operationId) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.cancelOperationById(operationId, SecurityContextHolder.getContext().getAuthentication()));
    }



}
