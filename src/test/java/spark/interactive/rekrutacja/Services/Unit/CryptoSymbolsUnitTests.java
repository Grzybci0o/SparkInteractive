package spark.interactive.rekrutacja.Services.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Exception.ResourceNotFoundException;
import spark.interactive.rekrutacja.Model.Symbols;
import spark.interactive.rekrutacja.Repository.CryptoSymbolsRepository;
import spark.interactive.rekrutacja.Service.impl.CryptoSymbolsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoSymbolsUnitTests {
    @Mock
    private CryptoSymbolsRepository cryptoSymbolsRepository;

    @InjectMocks
    private CryptoSymbolsServiceImpl cryptoSymbolsService;

    @Test
    public void testGetAll_Success() {
        Symbols symbol = new Symbols();
        symbol.setSymbol("BTCUSDT");
        when(cryptoSymbolsRepository.findAll()).thenReturn(List.of(symbol));

        List<SymbolsDto> result = cryptoSymbolsService.getAll();

        assertEquals(1, result.size());
        assertEquals("BTCUSDT", result.get(0).getSymbol());
    }

    @Test
    public void testCreateSymbol_Success() {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("BTCUSDT");

        Symbols symbol = new Symbols();
        symbol.setSymbol("BTCUSDT");
        when(cryptoSymbolsRepository.save(any())).thenReturn(symbol);

        SymbolsDto result = cryptoSymbolsService.createSymbol(symbolDto);

        assertEquals("BTCUSDT", result.getSymbol());
    }

    @Test
    public void testUpdateSymbol_Success() {
        String symbolName = "BTCUSDT";

        Symbols existingSymbol = new Symbols();
        existingSymbol.setSymbol(symbolName);

        when(cryptoSymbolsRepository.findBySymbol(symbolName)).thenReturn(Optional.of(existingSymbol));

        SymbolsDto newSymbol = new SymbolsDto();
        newSymbol.setSymbol("ETHUSDT");

        Symbols updatedSymbol = new Symbols();
        updatedSymbol.setSymbol("ETHUSDT");
        when(cryptoSymbolsRepository.save(existingSymbol)).thenReturn(updatedSymbol);

        SymbolsDto result = cryptoSymbolsService.updateSymbol(symbolName, newSymbol);

        assertEquals("ETHUSDT", result.getSymbol());
    }

    @Test
    public void testUpdateSymbol_NotFound() {
        String symbolName = "BTCUSDT";

        when(cryptoSymbolsRepository.findBySymbol(symbolName)).thenReturn(Optional.empty());

        SymbolsDto newSymbol = new SymbolsDto();
        newSymbol.setSymbol("ETHUSDT");

        assertThrows(ResourceNotFoundException.class, () -> cryptoSymbolsService.updateSymbol(symbolName, newSymbol));
    }
}
