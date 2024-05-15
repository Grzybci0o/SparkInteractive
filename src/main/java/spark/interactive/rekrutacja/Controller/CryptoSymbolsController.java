package spark.interactive.rekrutacja.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spark.interactive.rekrutacja.Dto.SymbolsDto;
import spark.interactive.rekrutacja.Service.CryptoSymbolsService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/symbols")
public class CryptoSymbolsController {

    private final CryptoSymbolsService cryptoSymbolsService;

    @GetMapping("/all")
    public ResponseEntity<List<SymbolsDto>> getCryptoRatesList() {
        List<SymbolsDto> symbols = cryptoSymbolsService.getAll();
        return ResponseEntity.ok(symbols);
    }

    @PostMapping("/createSymbol")
    public ResponseEntity<SymbolsDto> createNewSymbol(@RequestBody SymbolsDto symbol) {
        SymbolsDto createdSymbol = cryptoSymbolsService.createSymbol(symbol);
        return new ResponseEntity<>(createdSymbol, HttpStatus.CREATED);
    }

    @PatchMapping("/updateSymbol/{symbol}")
    public ResponseEntity<SymbolsDto> updateSymbol(@PathVariable String symbol, @RequestBody SymbolsDto newSymbol) {
        SymbolsDto updatedSymbol = cryptoSymbolsService.updateSymbol(symbol, newSymbol);
        return ResponseEntity.ok(updatedSymbol);
    }
}
