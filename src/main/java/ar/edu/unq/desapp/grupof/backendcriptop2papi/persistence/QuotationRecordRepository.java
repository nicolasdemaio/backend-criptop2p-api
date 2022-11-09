package ar.edu.unq.desapp.grupof.backendcriptop2papi.persistence;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.QuotationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuotationRecordRepository extends JpaRepository<QuotationRecord, Long> {

    @Query("from QuotationRecord q where q.timeStamp <= :dateTime AND q.cryptoCurrency = :cryptoCurrency")
    List<QuotationRecord> getQuotationsWithTimeStampBefore(@Param("cryptoCurrency") CryptoCurrency cryptoCurrency, @Param(
            "dateTime") LocalDateTime localDateTime);
}
