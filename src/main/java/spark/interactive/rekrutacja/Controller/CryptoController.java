package spark.interactive.rekrutacja.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spark.interactive.rekrutacja.Dto.CryptoRatesDto;
import spark.interactive.rekrutacja.Service.CryptoRatesService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/binance")
public class CryptoController {

    private final CryptoRatesService cryptoRatesService;

    @GetMapping("/last/{symbol}")
    public ResponseEntity<CryptoRatesDto> getCryptoRateForSymbol(@PathVariable("symbol") String symbol) {
        CryptoRatesDto crypto = cryptoRatesService.getCryptoRates(symbol);
        return ResponseEntity.ok(crypto);
    }

    @GetMapping("/lastday/{symbol}")
    public ResponseEntity<List<CryptoRatesDto>> getCryptoRateForSymbolByDay(@PathVariable("symbol") String symbol) {
        List<CryptoRatesDto> crypto = cryptoRatesService.getCryptoRatesForDay(symbol);
        return ResponseEntity.ok(crypto);
    }

    @GetMapping("/lastAll")
    public ResponseEntity<List<CryptoRatesDto>> getCryptoRatesList() {
        List<CryptoRatesDto> cryptoRatesDtoList = cryptoRatesService.getAllCryptoRates();
        return ResponseEntity.ok(cryptoRatesDtoList);
    }
}
