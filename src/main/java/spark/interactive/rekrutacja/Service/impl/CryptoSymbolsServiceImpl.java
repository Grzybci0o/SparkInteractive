package spark.interactive.rekrutacja.Service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Exception.ResourceNotFoundException;
import spark.interactive.rekrutacja.Mapper.CryptoSymbolMapper;
import spark.interactive.rekrutacja.Model.Symbols;
import spark.interactive.rekrutacja.Repository.CryptoSymbolsRepository;
import spark.interactive.rekrutacja.Service.CryptoSymbolsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoSymbolsServiceImpl implements CryptoSymbolsService {

    CryptoSymbolsRepository cryptoSymbolsRepository;

    @Override
    public List<SymbolsDto> getAll() {
        List<Symbols> symbols = cryptoSymbolsRepository.findAll();
        return symbols.stream().map(CryptoSymbolMapper::mapToCryptoSymbolsDto).collect(Collectors.toList());
    }

    @Override
    public SymbolsDto createSymbol(SymbolsDto symboldto) {
        Symbols symbol = CryptoSymbolMapper.mapToCryptoSymbols(symboldto);
        Symbols savedSymbol = cryptoSymbolsRepository.save(symbol);
        return CryptoSymbolMapper.mapToCryptoSymbolsDto(savedSymbol);
    }

    @Override
    public SymbolsDto updateSymbol(String symbol, SymbolsDto newSymbol) {
        Symbols foundSymbol = cryptoSymbolsRepository.findBySymbol(symbol)
                .orElseThrow(() -> new ResourceNotFoundException("Crypto symbol: " + symbol + " not found!"));

        foundSymbol.setSymbol(newSymbol.getSymbol());
        Symbols updatedSymbol = cryptoSymbolsRepository.save(foundSymbol);

        return CryptoSymbolMapper.mapToCryptoSymbolsDto(updatedSymbol);

    }

}
