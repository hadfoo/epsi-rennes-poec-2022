package fr.epsi.orel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.epsi.orel")

public class PizzaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PizzaApplication.class, args);

    }
}
