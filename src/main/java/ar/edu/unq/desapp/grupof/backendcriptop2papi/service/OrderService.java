package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.dto.MarketOrderDTO;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    /**
     * @return listado donde se muestran las intenciones activas de compra/venta
     */
    public List<MarketOrderDTO> getActiveOrders() {
        List<MarketOrder> orders = orderRepository.findActiveOrders();
        return orders.stream().map(order -> modelMapper.map(order, MarketOrderDTO.class)).toList();
    }
}
