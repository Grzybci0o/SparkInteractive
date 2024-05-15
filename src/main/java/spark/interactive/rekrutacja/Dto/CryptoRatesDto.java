package spark.interactive.rekrutacja.Dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoRatesDto {
    private Long id;
    private String symbol;
    private Double value;
    private Timestamp timestamp;
}
