package spark.interactive.rekrutacja.Controllers.IntegrationTests;

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
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Service.CryptoRatesService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CryptoControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CryptoRatesService cryptoRatesService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetCryptoRateForSymbol() throws Exception {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTC");

        when(cryptoRatesService.getCryptoRates(anyString())).thenReturn(cryptoRatesDto);

        mockMvc.perform(get("/api/binance/last/BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTC"));
    }

    @Test
    public void testGetCryptoRateForSymbolByDay() throws Exception {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTC");

        when(cryptoRatesService.getCryptoRatesForDay(anyString())).thenReturn(Collections.singletonList(cryptoRatesDto));

        mockMvc.perform(get("/api/binance/lastday/BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTC"));
    }

    @Test
    public void testGetCryptoRatesList() throws Exception {
        CryptoRatesDto cryptoRatesDto = new CryptoRatesDto();
        cryptoRatesDto.setSymbol("BTC");

        when(cryptoRatesService.getAllCryptoRates()).thenReturn(Collections.singletonList(cryptoRatesDto));

        mockMvc.perform(get("/api/binance/lastAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTC"));
    }
}
