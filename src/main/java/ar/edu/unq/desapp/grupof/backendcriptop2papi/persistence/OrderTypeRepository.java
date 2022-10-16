package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.orderType.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, String> {

}
