package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor,Long> {

    List<Investor> findByEmail(String email);

    boolean existsInvestorByEmail(String email);

    @Query("from Investor i where i.email = :email")
    Optional<Investor> findInvestorByEmail(@Param("email") String anEmail);

}
