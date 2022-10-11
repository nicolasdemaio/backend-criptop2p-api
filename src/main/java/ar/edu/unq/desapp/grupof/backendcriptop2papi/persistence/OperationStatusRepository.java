package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.operation.OperationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationStatusRepository extends JpaRepository<OperationStatus, String> {
}
