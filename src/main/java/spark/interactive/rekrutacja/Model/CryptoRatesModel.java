package spark.interactive.rekrutacja.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Crypto_rates")
public class CryptoRatesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "'value'")
    private Double value;
    @CreatedDate
    @Column(name = "timestamp")
    private Timestamp timestamp;
}
