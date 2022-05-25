package fr.epsi.rennes.poec.gweltaz.service;

import fr.epsi.rennes.poec.gweltaz.dao.PanierDAO;
import fr.epsi.rennes.poec.gweltaz.domain.Ingredient;
import fr.epsi.rennes.poec.gweltaz.domain.Panier;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PanierServiceTest {

    @InjectMocks
    private PanierService panierService;

    @Mock
    private PanierDAO panierDaoMock;

//    @Test
//    void addPizza() {
//    }

    @Test
    void getPanierById() {
        Panier panier = new Panier();
        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza1 = new Pizza();
        pizza1.setLabel("pizza 1");
        pizza1.setIngredients(new ArrayList<>());


        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(0);
        ingredient1.setLabel("ingredient 1");
        ingredient1.setNbCalories(100);
        ingredient1.setPrice(2);
        pizza1.getIngredients().add(ingredient1);


        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId(1);
        ingredient1.setLabel("ingredient 2");
        ingredient1.setNbCalories(50);
        ingredient1.setPrice(3);
        pizza1.getIngredients().add(ingredient2);

        Pizza pizza2 = new Pizza();
        pizza2.setIngredients(new ArrayList<>());
        pizza2.getIngredients().add(ingredient1);

        pizzas.add(pizza1);
        pizzas.add(pizza2);
        panier.setPizzas(pizzas);

        final int PANIER_ID = 0;
        Mockito.when(panierDaoMock.getPanierById(PANIER_ID)).thenReturn(panier);

        // When
        Panier result = panierService.getPanierById(PANIER_ID);
        double prixPizza1 = result.getPizzas().get(0).getPrice();
        double prixPizza2 = result.getPizzas().get(1).getPrice();
        double prixPanier = result.getTotalPrice();

        //Then
        Assertions.assertThat(prixPizza1)
                .isEqualTo(ingredient1.getPrice() + ingredient2.getPrice());
        Assertions.assertThat(prixPizza2).isEqualTo(ingredient1.getPrice());
        Assertions.assertThat(prixPanier).isEqualTo(prixPizza1 + prixPizza2);

    }

//    @Test
//    void removePizza() {
//    }
}