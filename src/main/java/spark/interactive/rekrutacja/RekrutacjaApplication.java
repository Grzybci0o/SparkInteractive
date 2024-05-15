package spark.interactive.rekrutacja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import spark.interactive.rekrutacja.Repository.CryptoRepository;
import spark.interactive.rekrutacja.Repository.CryptoSymbolsRepository;
import spark.interactive.rekrutacja.Service.BinanceService;

@SpringBootApplication
@EnableScheduling
public class RekrutacjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RekrutacjaApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.create("https://api.binance.com/api/v3");
	}

	@Bean
	public BinanceService binanceService(WebClient webClient, CryptoRepository cryptoRepository, CryptoSymbolsRepository cryptoSymbolsRepository) {
		return new BinanceService(webClient, cryptoRepository, cryptoSymbolsRepository);
	}

}
