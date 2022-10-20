package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.InvestmentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccount,Long> {
    @Query("select account from InvestmentAccount account where account.investor.id = :investor")
    InvestmentAccount findInvestmentAccountByInvestor(@Param("investor") Long investorId);
}
