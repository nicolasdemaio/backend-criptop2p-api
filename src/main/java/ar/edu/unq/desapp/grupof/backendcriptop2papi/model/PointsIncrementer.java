package ar.edu.unq.desapp.grupof.backendcriptop2papi.model;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.Operation;

import java.time.LocalDateTime;

public class PointsIncrementer {

    public static final int POINTS_BEFORE_THIRTY_MINUTES = 10;
    public static final int POINTS_AFTER_THIRTY_MINUTES = 5;
    private Operation operation;

    public PointsIncrementer(Operation anOperation) {
        operation = anOperation;
    }

    public void incrementPointsConsidering(LocalDateTime transactionDateTime) {
        Integer pointsToIncrease = pointsToIncrementFor(transactionDateTime);
        increaseToInvolvedAccounts(pointsToIncrease);
    }

    private void increaseToInvolvedAccounts(Integer anAmountOfPoints) {
        operation.getParty().addPoints(anAmountOfPoints);
        operation.getCounterparty().addPoints(anAmountOfPoints);
    }

    private Integer pointsToIncrementFor(LocalDateTime transactionDateTime) {
        LocalDateTime initialTimeOfOperationPlus30Minutes = operation.getDateTimeOfOrigin().plusMinutes(30);
        if (transactionDateTime.isBefore(initialTimeOfOperationPlus30Minutes)) {
            return POINTS_BEFORE_THIRTY_MINUTES;
        } else {
            return POINTS_AFTER_THIRTY_MINUTES;
        }
    }

}
