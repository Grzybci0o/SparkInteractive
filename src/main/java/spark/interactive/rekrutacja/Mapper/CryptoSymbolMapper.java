package spark.interactive.rekrutacja.Mapper;

import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Model.Symbols;

public class CryptoSymbolMapper {
    public static SymbolsDto mapToCryptoSymbolsDto(Symbols symbols) {
        return new SymbolsDto(
                symbols.getId(),
                symbols.getSymbol());
    }

    public static Symbols mapToCryptoSymbols(SymbolsDto symbolsDto) {
        return new Symbols(
                symbolsDto.getId(),
                symbolsDto.getSymbol());
    }
}
