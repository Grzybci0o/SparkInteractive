package spark.interactive.rekrutacja.Service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Mapper.CryptoRatesMapper;
import spark.interactive.rekrutacja.Model.BinanceResponse;
import spark.interactive.rekrutacja.Model.Symbols;
import spark.interactive.rekrutacja.Repository.CryptoRepository;
import spark.interactive.rekrutacja.Repository.CryptoSymbolsRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BinanceService {
    private final WebClient webClient;
    private final CryptoRepository cryptoRatesRepository;
    private final CryptoSymbolsRepository cryptoSymbolsRepository;

    @Scheduled(fixedRate = 600000)
    public void fetchAndSaveData() {
        List<Symbols> symbols = cryptoSymbolsRepository.findAll();

        for (Symbols symbol : symbols) {
            BinanceResponse binanceResponse = webClient.get()
                    .uri("/ticker?symbol=" + symbol.getSymbol())
                    .retrieve()
                    .bodyToMono(BinanceResponse.class)
                    .block();

            if (binanceResponse != null) {
                CryptoRatesDto cryptoRatesDto = CryptoRatesMapper.mapToCryptoRatesDto(binanceResponse);
                cryptoRatesDto.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
                cryptoRatesRepository.save(CryptoRatesMapper.mapToCryptoRates(cryptoRatesDto));
            }
        }
    }
}
