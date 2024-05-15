package spark.interactive.rekrutacja.Controllers.UnitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spark.interactive.rekrutacja.Controller.CryptoController;
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Service.CryptoRatesService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoControllerUnitTests {
    @Mock
    private CryptoRatesService cryptoRatesService;

    @InjectMocks
    private CryptoController cryptoController;

    @Test
    public void testGetCryptoRateForSymbol_Success() {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTCUSDT");

        when(cryptoRatesService.getCryptoRates(anyString())).thenReturn(cryptoRatesDto);

        ResponseEntity<CryptoRatesDto> response = cryptoController.getCryptoRateForSymbol("BTCUSDT");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BTCUSDT", response.getBody().getSymbol());
    }

    @Test
    public void testGetCryptoRateForSymbolByDay_Success() {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTCUSDT");

        when(cryptoRatesService.getCryptoRatesForDay(anyString())).thenReturn(Collections.singletonList(cryptoRatesDto));

        ResponseEntity<List<CryptoRatesDto>> response = cryptoController.getCryptoRateForSymbolByDay("BTCUSDT");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BTCUSDT", response.getBody().get(0).getSymbol());
    }

    @Test
    public void testGetCryptoRatesList_Success() {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTCUSDT");

        when(cryptoRatesService.getAllCryptoRates()).thenReturn(Collections.singletonList(cryptoRatesDto));

        ResponseEntity<List<CryptoRatesDto>> response = cryptoController.getCryptoRatesList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BTCUSDT", response.getBody().get(0).getSymbol());
    }
}
