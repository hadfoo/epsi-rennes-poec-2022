package fr.rennes.epsi.poec.bob.guitarshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "fr.rennes.epsi.poec.bob.guitarshop")
public class GuitarShopApp {
    public static void main(String[] args) {
        System.out.println("Start Guitar Shop");
        SpringApplication.run(GuitarShopApp.class, args);
    }
}
