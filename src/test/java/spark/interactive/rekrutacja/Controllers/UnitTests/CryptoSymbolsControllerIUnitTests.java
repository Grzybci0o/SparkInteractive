package spark.interactive.rekrutacja.Controllers.UnitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spark.interactive.rekrutacja.Controller.CryptoSymbolsController;
import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Service.CryptoSymbolsService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoSymbolsControllerIUnitTests {

    @Mock
    private CryptoSymbolsService cryptoSymbolsService;

    @InjectMocks
    private CryptoSymbolsController cryptoSymbolsController;

    @Test
    public void testGetAll_Success() {
        SymbolsDto symbol = new SymbolsDto();
        symbol.setSymbol("BTCUSDT");

        when(cryptoSymbolsService.getAll()).thenReturn(Collections.singletonList(symbol));

        ResponseEntity<List<SymbolsDto>> response = cryptoSymbolsController.getCryptoRatesList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BTCUSDT", response.getBody().get(0).getSymbol());
    }

    @Test
    public void testCreateNewSymbol_Success() {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("BTCUSDT");

        when(cryptoSymbolsService.createSymbol(any())).thenReturn(symbolDto);

        ResponseEntity<SymbolsDto> response = cryptoSymbolsController.createNewSymbol(symbolDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("BTCUSDT", response.getBody().getSymbol());
    }

    @Test
    public void testUpdateSymbol_Success() {
        String symbolName = "BTCUSDT";

        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("ETHUSDT");

        when(cryptoSymbolsService.updateSymbol(anyString(), any())).thenReturn(symbolDto);

        ResponseEntity<SymbolsDto> response = cryptoSymbolsController.updateSymbol(symbolName, symbolDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ETHUSDT", response.getBody().getSymbol());
    }
}
