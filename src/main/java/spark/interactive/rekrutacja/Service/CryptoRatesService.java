package spark.interactive.rekrutacja.Service;

import spark.interactive.rekrutacja.Dto.CryptoRatesDto;

import java.util.List;

public interface CryptoRatesService {

    CryptoRatesDto getCryptoRates(String symbol);

    List<CryptoRatesDto> getCryptoRatesForDay(String symbol);

    List<CryptoRatesDto> getAllCryptoRates();
}
