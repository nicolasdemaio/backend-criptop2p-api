package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.OperationStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class InvestmentAccount implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<MarketOrder> marketOrders;
    @OneToOne
    private Investor investor;
    @OneToMany
    private List<Operation> operations;

    private int points;

    private static final int POINTS_LOST_FOR_CANCELLING = 20;

    protected InvestmentAccount() { }

    public InvestmentAccount(Investor anInvestor) {
        marketOrders = new ArrayList<MarketOrder>();
        investor = anInvestor;
        operations = new ArrayList<Operation>();
        points = 0;
    }

    public void placeMarketOrder(MarketOrder aMarketOrder) {
        marketOrders.add(aMarketOrder);
    }

    public void applyFor(MarketOrder aMarketOrder) {
        aMarketOrder.beginAnOperationBy(this);
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
                .collect(Collectors.toList())
                .size();
        if (completedOperations != 0) {
            reputation = Math.round(this.points / completedOperations);
        }
        return reputation;
    }
}
