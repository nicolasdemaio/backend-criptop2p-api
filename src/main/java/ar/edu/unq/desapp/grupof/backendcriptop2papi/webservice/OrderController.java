package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OperationDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OrderForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping (path = "/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Construir un listado donde se muestran las intenciones activas de compra/venta
     */
    @Operation (summary = "Get a list of all current active Market Orders")
    @GetMapping
    public ResponseEntity<List<MarketOrderDTO>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    @Operation (summary = "Place a new Market Order")
    @PostMapping
    public ResponseEntity<MarketOrderDTO> placeMarketOrder(@RequestBody @Valid OrderForm form){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeMarketOrder(form, authentication()));
    }

    @Operation (summary = "Apply for an active Market Order")
    @PostMapping (path = "/{orderId}")
    public ResponseEntity<OperationDTO> applyForOrder(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.applyForOrder(orderId, authentication()));
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
