package fr.epsi.rennes.poec.manucre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.rennes.poec.manucre")
public class PizzaApplication {
	/*
	 * Class de lancement de l'application
	 */
	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

}
