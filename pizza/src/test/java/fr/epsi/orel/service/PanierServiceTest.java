package fr.epsi.orel.service;

import fr.epsi.orel.dao.PanierDAO;
import fr.epsi.orel.domain.Ingredient;
import fr.epsi.orel.domain.Panier;
import fr.epsi.orel.domain.Pizza;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PanierServiceTest {

    @InjectMocks
    private PanierService panierService;

    @Mock
    private PanierDAO panierDaoMock;

    @Test
    public void getPrixPanier() {
        //given
        Panier panier = new Panier();
        List<Pizza> pizzas = new ArrayList<>();

        Pizza pizza1 = new Pizza();
        pizza1.setLabel("pizza 1");
        pizza1.setIngredients(new ArrayList<>());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(0);
        ingredient1.setLabel("ingredient 1");
        ingredient1.setNbCalories(100);
        ingredient1.setPrix(2);
        pizza1.getIngredients().add(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1);
        ingredient2.setLabel("ingredient 2");
        ingredient2.setNbCalories(50);
        ingredient2.setPrix(1.5);
        pizza1.getIngredients().add(ingredient2);

        pizzas.add(pizza1);
        panier.setPizzas(pizzas);

        double prixPanier = ingredient1.getPrix() + ingredient2.getPrix();

        final int PANIER_ID = 0;
        Mockito.when(panierDaoMock.getPrixPanier(PANIER_ID)).thenReturn(prixPanier);
        //when
        double result = panierService.getPrixPanier(PANIER_ID);
        //then
        Assertions.assertThat(result)
                .isEqualTo(ingredient1.getPrix() + ingredient2.getPrix());

    }
}
