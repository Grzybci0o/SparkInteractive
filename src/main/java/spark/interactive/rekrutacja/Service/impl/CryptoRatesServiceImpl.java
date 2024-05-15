package spark.interactive.rekrutacja.Service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Exception.ResourceNotFoundException;
import spark.interactive.rekrutacja.Mapper.CryptoRatesMapper;
import spark.interactive.rekrutacja.Model.CryptoRatesModel;
import spark.interactive.rekrutacja.Repository.CryptoRepository;
import spark.interactive.rekrutacja.Service.CryptoRatesService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoRatesServiceImpl implements CryptoRatesService {

    private CryptoRepository cryptoRepository;

    @Override
    public CryptoRatesDto getCryptoRates(String symbol) {
        CryptoRatesModel cryptoRate = cryptoRepository.findTopBySymbolOrderByTimestampDesc(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Crypto symbol: " + symbol + " not found in database!"));
        return CryptoRatesMapper.mapToCryptoRatesDto(cryptoRate);
    }

    @Override
    public List<CryptoRatesDto> getCryptoRatesForDay(String symbol) {
        Timestamp yesterday = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        List<CryptoRatesModel> cryptoRatesForDay = cryptoRepository.findBySymbolAndTimestampAfter(symbol, yesterday);
        if (cryptoRatesForDay.isEmpty()) {
            throw new ResourceNotFoundException("No crypto rates found for symbol: " + symbol + " in the last 24 hours!");
        }
        return cryptoRatesForDay.stream().map(CryptoRatesMapper::mapToCryptoRatesDto).collect(Collectors.toList());
    }

    @Override
    public List<CryptoRatesDto> getAllCryptoRates() {
        List<CryptoRatesModel> latestCryptoRates = cryptoRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(CryptoRatesModel::getId)
                        .reversed())
                .collect(Collectors.groupingBy(CryptoRatesModel::getSymbol))
                .values()
                .stream()
                .map(list -> list.stream()
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull).toList();

        if (latestCryptoRates.isEmpty()) {
            throw new ResourceNotFoundException("No crypto rates found in the database!");
        }

        return latestCryptoRates.stream()
                .sorted(Comparator.comparing(CryptoRatesModel::getId))
                .map(CryptoRatesMapper::mapToCryptoRatesDto)
                .collect(Collectors.toList());
    }
}
