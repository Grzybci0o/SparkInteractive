package spark.interactive.rekrutacja.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spark.interactive.rekrutacja.Model.Symbols;

import java.util.Optional;

public interface CryptoSymbolsRepository extends JpaRepository<Symbols, Long> {
    Optional<Symbols> findBySymbol(String symbol);
}
