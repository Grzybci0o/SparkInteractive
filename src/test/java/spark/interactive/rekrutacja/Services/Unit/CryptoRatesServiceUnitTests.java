package spark.interactive.rekrutacja.Services.Unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Exception.ResourceNotFoundException;
import spark.interactive.rekrutacja.Model.CryptoRatesModel;
import spark.interactive.rekrutacja.Repository.CryptoRepository;
import spark.interactive.rekrutacja.Service.impl.CryptoRatesServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoRatesServiceUnitTests {
    @Mock
    private CryptoRepository cryptoRepository;

    @InjectMocks
    private CryptoRatesServiceImpl cryptoRatesService;

    @Test
    public void testGetCryptoRates_Success() {
        CryptoRatesModel cryptoRatesModel = CryptoRatesModel.builder()
                .symbol("BTCUSDT")
                .value(61234.4)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        when(cryptoRepository.findTopBySymbolOrderByTimestampDesc(any())).thenReturn(Optional.of(cryptoRatesModel));

        CryptoRatesDto result = cryptoRatesService.getCryptoRates("BTCUSDT");

        assertEquals("BTCUSDT", result.getSymbol());
        assertEquals(61234.4, result.getValue());
    }

    @Test
    public void testGetCryptoRates_NotFound() {
        when(cryptoRepository.findTopBySymbolOrderByTimestampDesc(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cryptoRatesService.getCryptoRates("BTCUSDT2"));
    }

    @Test
    public void testGetCryptoRatesForDay_Success() {
        CryptoRatesModel cryptoRatesModel = new CryptoRatesModel();
        cryptoRatesModel.setSymbol("BTCUSDT");
        cryptoRatesModel.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        when(cryptoRepository.findBySymbolAndTimestampAfter(any(), any())).thenReturn(Collections.singletonList(cryptoRatesModel));

        List<CryptoRatesDto> result = cryptoRatesService.getCryptoRatesForDay("BTCUSDT");

        assertEquals(1, result.size());
        assertEquals("BTCUSDT", result.get(0).getSymbol());
    }

    @Test
    public void testGetCryptoRatesForDay_NotFound() {
        when(cryptoRepository.findBySymbolAndTimestampAfter(any(), any())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> cryptoRatesService.getCryptoRatesForDay("BTCUSDT"));
    }

    @Test
    public void testGetAllCryptoRates_Success() {
        CryptoRatesModel cryptoRatesModel1 = new CryptoRatesModel();
        cryptoRatesModel1.setId(1L);
        cryptoRatesModel1.setSymbol("BTCUSDT");
        cryptoRatesModel1.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        CryptoRatesModel cryptoRatesModel2 = new CryptoRatesModel();
        cryptoRatesModel2.setId(2L);
        cryptoRatesModel2.setSymbol("ETHUSDT");
        cryptoRatesModel2.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        when(cryptoRepository.findAll()).thenReturn(List.of(cryptoRatesModel1, cryptoRatesModel2));

        List<CryptoRatesDto> result = cryptoRatesService.getAllCryptoRates();

        assertEquals(2, result.size());
        assertEquals("BTCUSDT", result.get(0).getSymbol());
        assertEquals("ETHUSDT", result.get(1).getSymbol());
    }

    @Test
    public void testGetAllCryptoRates_NotFound() {
        when(cryptoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> cryptoRatesService.getAllCryptoRates());
    }
}
