package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

// 1. se toma por una cuenta
// 2. esa cuenta transacciona contra la orden
// 3. se crea la operacion en las cuentas parte y contraparte
// 4. la orden no se usa mas, queda Tomada por esa cuenta.

@Getter
@Entity
public class MarketOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final String cryptoCurrency;
    @ManyToOne
    private final InvestmentAccount emitter;
    private final Double nominalQuantity;
    private final Double desiredPrice;
    @ManyToOne
    private final OrderType orderType;
    private final Double actualPrice;
    private final LocalDateTime dateTime;
    private final Double ACCEPTED_PRICE_FLUCTUATION = 0.05d;
    private Boolean isTaken;


    public MarketOrder(String cryptoCurrency, InvestmentAccount emitter, Double nominalQuantity, Double desiredPrice, OrderType orderType, Double actualPrice, LocalDateTime dateTime) {
        // Nico: extraería la validación a un objeto quiza
        validateThatPriceFluctuationIsAllowed(desiredPrice, actualPrice);
        this.cryptoCurrency = cryptoCurrency;
        this.emitter = emitter;
        this.nominalQuantity = nominalQuantity;
        this.desiredPrice = desiredPrice;
        this.orderType = orderType;
        this.actualPrice = actualPrice;
        this.dateTime = dateTime;
        isTaken = false;
    }

    public Operation beginAnOperationBy(InvestmentAccount anInvestmentAccount) {
        if (isTaken) throw new OrderAlreadyTakenException();
        if (isEmitter(anInvestmentAccount)) throw new InvalidOperationException("An order cannot be taken by its emitter");
        isTaken = true;
        // Generate operation and give it to both accounts.
        Operation operation = new Operation(this, emitter, anInvestmentAccount);
        emitter.addOperation(operation);
        anInvestmentAccount.addOperation(operation);

        return operation;
    }

    private void validateThatPriceFluctuationIsAllowed(Double desiredPrice, Double actualPrice) {
        if (isAboveAllowedPrice(desiredPrice, actualPrice) || isBelowAllowedPrice(desiredPrice, actualPrice)) {
            throw new InvalidOrderPriceException("Desired price should be in the range of +/- 5% from current price");
        }
    }

    private boolean isBelowAllowedPrice(Double desiredPrice, Double actualPrice) {
        return desiredPrice <= (actualPrice - actualPrice * ACCEPTED_PRICE_FLUCTUATION);
    }

    private boolean isAboveAllowedPrice(Double desiredPrice, Double actualPrice) {
        return desiredPrice >= (actualPrice + actualPrice * ACCEPTED_PRICE_FLUCTUATION);
    }

    private boolean isEmitter(InvestmentAccount anInvestmentAccount){
        return anInvestmentAccount.equals(this.emitter);
    }
}


