package spark.interactive.rekrutacja.Services.Integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spark.interactive.rekrutacja.Model.CryptoRatesModel;
import spark.interactive.rekrutacja.Repository.CryptoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CryptoRatesServiceIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CryptoRepository cryptoRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetCryptoRates_Success() throws Exception {
        CryptoRatesModel cryptoRatesModel = new CryptoRatesModel();
        cryptoRatesModel.setSymbol("BTCUSDT");
        cryptoRatesModel.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        when(cryptoRepository.findTopBySymbolOrderByTimestampDesc(any())).thenReturn(Optional.of(cryptoRatesModel));

        mockMvc.perform(get("/api/binance/last/BTCUSDT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"));
    }

    @Test
    public void testGetCryptoRatesForDay_Success() throws Exception {
        CryptoRatesModel cryptoRatesModel = new CryptoRatesModel();
        cryptoRatesModel.setSymbol("BTCUSDT");
        cryptoRatesModel.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

        when(cryptoRepository.findBySymbolAndTimestampAfter(any(), any())).thenReturn(Collections.singletonList(cryptoRatesModel));

        mockMvc.perform(get("/api/binance/lastday/BTCUSDT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }

    @Test
    public void testGetAllCryptoRates_Success() throws Exception {
        CryptoRatesModel cryptoRatesModel = new CryptoRatesModel();
        cryptoRatesModel.setSymbol("BTCUSDT");
        cryptoRatesModel.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        when(cryptoRepository.findAll()).thenReturn(Collections.singletonList(cryptoRatesModel));

        mockMvc.perform(get("/api/binance/lastAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }
}
