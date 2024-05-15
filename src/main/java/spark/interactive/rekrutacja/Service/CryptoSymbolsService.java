package spark.interactive.rekrutacja.Service;

import spark.interactive.rekrutacja.Dto.SymbolsDto;

import java.util.List;

public interface CryptoSymbolsService {
    List<SymbolsDto> getAll();

    SymbolsDto createSymbol(SymbolsDto symbol);

    SymbolsDto updateSymbol(String symbol, SymbolsDto newSymbol);
}
