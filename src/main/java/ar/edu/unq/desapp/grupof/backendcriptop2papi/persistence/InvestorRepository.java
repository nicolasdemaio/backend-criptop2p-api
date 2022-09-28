package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorRepository extends JpaRepository<Investor,Long> {

    List<Investor> findByEmail(String email);

    boolean existsInvestorByEmail(String email);

}
