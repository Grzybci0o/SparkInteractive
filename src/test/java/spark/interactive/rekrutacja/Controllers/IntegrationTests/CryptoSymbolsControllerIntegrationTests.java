package spark.interactive.rekrutacja.Controllers.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spark.interactive.rekrutacja.Dto.SymbolsDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CryptoSymbolsControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllSymbols() throws Exception {
        mockMvc.perform(get("/api/symbols/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].symbol").value("ETHUSDT"))
                .andExpect(jsonPath("$[1].symbol").value("LTCUSDT"));
    }

    @Test
    void testCreateSymbol() throws Exception {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("ETHUSDT");

        mockMvc.perform(post("/api/symbols/createSymbol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(symbolDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("ETHUSDT"));
    }

    @Test
    void testUpdateSymbol() throws Exception {
        SymbolsDto symbolDto = new SymbolsDto();
        symbolDto.setSymbol("ETHUSDT");

        mockMvc.perform(patch("/api/symbols/updateSymbol/BTCUSDT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(symbolDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol").value("ETHUSDT"));
    }
}
