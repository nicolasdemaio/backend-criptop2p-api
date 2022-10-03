package ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.OrderForm;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<List<MarketOrderDTO>> getActiveOrders() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    @PostMapping
    public ResponseEntity<MarketOrderDTO> placeMarketOrder(@RequestBody OrderForm form, Authentication authentication){
        return ResponseEntity.status(201).body(orderService.placeMarketOrder(form, authentication));
    }

}
