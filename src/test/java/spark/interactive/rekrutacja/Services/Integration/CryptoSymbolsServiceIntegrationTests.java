package spark.interactive.rekrutacja.Services.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Mapper.CryptoSymbolMapper;
import spark.interactive.rekrutacja.Model.Symbols;
import spark.interactive.rekrutacja.Repository.CryptoSymbolsRepository;
import spark.interactive.rekrutacja.Service.CryptoSymbolsService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CryptoSymbolsServiceIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private CryptoSymbolsRepository cryptoSymbolsRepository;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllSymbols_Success() throws Exception {
        Symbols symbol = new Symbols();
        symbol.setSymbol("BTCUSDT");

        when(cryptoSymbolsRepository.findAll()).thenReturn(Collections.singletonList(symbol));

        mockMvc.perform(get("/api/symbols/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }

    @Test
    public void testCreateSymbol_Success() throws Exception {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("BTCUSDT2");

        Symbols symbol = CryptoSymbolMapper.mapToCryptoSymbols(symbolDto);

        when(cryptoSymbolsRepository.save(any())).thenReturn(symbol);

        mockMvc.perform(post("/api/symbols/createSymbol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(symbolDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT2"));
    }

    @Test
    public void testUpdateSymbol_Success() throws Exception {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("BTCUSDT");

        Symbols symbol = CryptoSymbolMapper.mapToCryptoSymbols(symbolDto);

        when(cryptoSymbolsRepository.findBySymbol(any())).thenReturn(Optional.of(symbol));
        when(cryptoSymbolsRepository.save(any())).thenReturn(symbol);

        mockMvc.perform(patch("/api/symbols/updateSymbol/BTC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(symbolDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"));
    }
}
