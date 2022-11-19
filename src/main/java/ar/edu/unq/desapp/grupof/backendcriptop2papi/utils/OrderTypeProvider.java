package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderTypeException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.PurchaseOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.SalesOrder;

import java.util.Map;

public class OrderTypeProvider {

    public static OrderType getOrderTypeAccordingTo(String anOrderType) {
        Map<String, OrderType> orderTypes = Map.ofEntries(Map.entry("SALES", new SalesOrder()), Map.entry("PURCHASE", new PurchaseOrder()));
        OrderType orderType = orderTypes.get(anOrderType);
        if (orderType == null) throw new InvalidOrderTypeException();
        return orderType;
    }

}
