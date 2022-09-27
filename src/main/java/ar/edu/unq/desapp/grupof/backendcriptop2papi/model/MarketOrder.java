package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOperationException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.InvalidOrderPriceException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.NotSuitablePriceException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.exceptions.OrderAlreadyTakenException;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class MarketOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency cryptoCurrency;
    @ManyToOne
    private InvestmentAccount emitter;
    private Double nominalQuantity;
    private Double desiredPrice;
    @ManyToOne
    private OrderType orderType;
    private Double actualPrice;
    private LocalDateTime dateTime;
    private static final Double ACCEPTED_PRICE_FLUCTUATION = 0.05d;
    private Boolean isTaken;


    public MarketOrder(CryptoCurrency cryptoCurrency, InvestmentAccount emitter, Double nominalQuantity, Double desiredPrice, OrderType orderType, Double actualPrice, LocalDateTime dateTime) {
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

    protected MarketOrder() { }

    public Operation beginAnOperationBy(InvestmentAccount anInvestmentAccount, CryptoQuotation currentQuotation) {
        if (isTaken) throw new OrderAlreadyTakenException();
        if (isEmitter(anInvestmentAccount)) throw new InvalidOperationException("An order cannot be taken by its emitter");
        validateIfPriceIsAccordingToDesired(anInvestmentAccount, currentQuotation);
        isTaken = true;
        // Generate operation and give it to both accounts.
        Operation operation = new Operation(this, emitter, anInvestmentAccount, currentQuotation);
        emitter.addOperation(operation);
        anInvestmentAccount.addOperation(operation);

        return operation;
    }

    private void validateIfPriceIsAccordingToDesired(InvestmentAccount anInvestmentAccount, CryptoQuotation currentQuotation) {
        if (! orderType.isSuitablePrice(currentQuotation, desiredPrice)){
            Operation operation = new Operation(this, emitter, anInvestmentAccount, currentQuotation);
            emitter.addOperation(operation);
            anInvestmentAccount.addOperation(operation);
            operation.systemCancel();
            throw new NotSuitablePriceException("The current price of the asset does not fulfil the expectations from the emitter");
        }
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


