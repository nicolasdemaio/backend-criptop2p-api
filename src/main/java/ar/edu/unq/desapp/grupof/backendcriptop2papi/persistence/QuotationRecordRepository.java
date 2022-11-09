package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoQuotation;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.MarketOrder;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.QuotationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRecordRepository extends JpaRepository<CryptoQuotation, Long> {

    /*@Query("from QuotationRecord q where q.timestamp >= NOW() - INTERVAL 1 DAY AND q.cryptoCurrency = :cryptoCurrency")
    List<QuotationRecord> getLast24HourQuotations(@Param("cryptoCurrency")CryptoCurrency cryptoCurrency);*/
}
