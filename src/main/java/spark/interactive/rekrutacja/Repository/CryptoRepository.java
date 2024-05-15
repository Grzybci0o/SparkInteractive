package spark.interactive.rekrutacja.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spark.interactive.rekrutacja.Model.CryptoRatesModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CryptoRepository extends JpaRepository<CryptoRatesModel, Long> {
    Optional<CryptoRatesModel> findTopBySymbolOrderByTimestampDesc(String symbol);
    List<CryptoRatesModel> findBySymbolAndTimestampAfter(String symbol, Timestamp timestamp);
}
