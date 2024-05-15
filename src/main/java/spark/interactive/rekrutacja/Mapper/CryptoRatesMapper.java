package spark.interactive.rekrutacja.Mapper;

import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Model.BinanceResponse;
import spark.interactive.rekrutacja.Model.CryptoRatesModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CryptoRatesMapper {

    public static CryptoRatesDto mapToCryptoRatesDto(CryptoRatesModel cryptoRatesModel) {
        return CryptoRatesDto.builder()
                .id(cryptoRatesModel.getId())
                .symbol(cryptoRatesModel.getSymbol())
                .value(cryptoRatesModel.getValue())
                .timestamp(cryptoRatesModel.getTimestamp())
                .build();
    }

    public static CryptoRatesModel mapToCryptoRates(CryptoRatesDto cryptoRatesDto) {
        return CryptoRatesModel.builder()
                .id(cryptoRatesDto.getId())
                .symbol(cryptoRatesDto.getSymbol())
                .value(cryptoRatesDto.getValue())
                .timestamp(cryptoRatesDto.getTimestamp())
                .build();
    }

    public static CryptoRatesDto mapToCryptoRatesDto(BinanceResponse binanceResponse) {
        return CryptoRatesDto.builder()
                .symbol(binanceResponse.getSymbol())
                .value(binanceResponse.getLastPrice())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
