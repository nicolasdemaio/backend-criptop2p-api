package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class InvestmentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<MarketOrder> marketOrders;
    @OneToOne
    private Investor investor;
    @OneToMany
    private List<Operation> operations;

    private Integer points;

    private static final int POINTS_LOST_FOR_CANCELLING = 20;

    protected InvestmentAccount() { }

    public InvestmentAccount(Investor anInvestor) {
        marketOrders = new ArrayList<>();
        investor = anInvestor;
        operations = new ArrayList<>();
        points = 0;
    }

    public void placeMarketOrder(MarketOrder aMarketOrder) {
        marketOrders.add(aMarketOrder);
    }

    public Operation applyFor(MarketOrder aMarketOrder, CryptoQuotation currentQuotation) {
        return aMarketOrder.beginAnOperationBy(this, currentQuotation);
    }

    public void addOperation(Operation anOperation) {
        operations.add(anOperation);
    }

    public void discountPointsForCancellation() {
        this.points -= POINTS_LOST_FOR_CANCELLING;
    }

    public int getReputation() {
        int reputation = 0;
        int completedOperations = operations
                .stream()
                .filter(Operation::isCompleted)
                .toList()
                .size();
        if (completedOperations != 0) {
            reputation = this.points / completedOperations;
        }
        return reputation;
    }

    public void addPoints(Integer anAmountOfPoints) {
        points += anAmountOfPoints;
    }
}
