package fr.epsi.rennes.poec.antoine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.rennes.poec.antoine")
public class GaletteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GaletteApplication.class, args);
	}
}
