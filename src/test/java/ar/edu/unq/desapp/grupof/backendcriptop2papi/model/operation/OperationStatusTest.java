package ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStatusTest {

    @Test
    void aNewOperationIsActive(){
        OperationStatus newOperationStatus = new NewOperationStatus();

        Assertions.assertTrue(newOperationStatus.isActive());
    }

    @Test
    void inProgressOperationIsActive(){
        OperationStatus inProgressOperationStatus = new InProgressStatus();

        Assertions.assertTrue(inProgressOperationStatus.isActive());
    }

    @Test
    void aCompletedOperationIsNotActive(){
        OperationStatus completedOperationStatus = new CompletedStatus();

        Assertions.assertFalse(completedOperationStatus.isActive());
    }

    @Test
    void aCancelledOperationIsNotActive(){
        OperationStatus cancalledOperationStatus = new CancelledStatus();

        Assertions.assertFalse(cancalledOperationStatus.isActive());
    }
}
