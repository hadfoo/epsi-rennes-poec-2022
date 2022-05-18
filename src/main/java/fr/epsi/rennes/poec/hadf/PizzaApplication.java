package fr.epsi.rennes.poec.hadf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.rennes.poec.hadf")
public class PizzaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

}
