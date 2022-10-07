package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.OrderType;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.PurchaseOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.SalesOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderTypeException;

import java.util.Map;

public class OrderTypeProvider {

    public static OrderType getOrderTypeConsideringADescription(String operationType) {
        Map<String, OrderType> orderTypes = Map.ofEntries(Map.entry("SALES", new SalesOrder()), Map.entry("PURCHASE", new PurchaseOrder()));
        OrderType orderType = orderTypes.get(operationType);
        if (orderType == null) throw new InvalidOrderTypeException();
        return orderType;
    }

}
