package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccount,Long> {

}
