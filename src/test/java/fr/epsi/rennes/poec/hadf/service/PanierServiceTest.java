package fr.epsi.rennes.poec.hadf.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import fr.epsi.rennes.poec.hadf.dao.PanierDAO;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.domain.Panier;
import fr.epsi.rennes.poec.hadf.domain.Pizza;

@SpringBootTest
public class PanierServiceTest {
	
	@InjectMocks
	private PanierService panierService;
	
	@Mock
	private PanierDAO panierDaoMock;
	
	@Test
	public void getPanierById() {
		// given
		Panier panier = new Panier();
		List<Pizza> pizzas = new ArrayList<>();
		
		Pizza pizza1 = new Pizza();
		pizza1.setLibelle("Pizza 1");
		pizza1.setIngredients(new ArrayList<>());
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(0);
		ingredient1.setLibelle("ingredient 1");
		ingredient1.setNbCalories(100);
		ingredient1.setPrix(2);
		pizza1.getIngredients().add(ingredient1);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(1);
		ingredient2.setLibelle("ingredient 2");
		ingredient2.setNbCalories(50);
		ingredient2.setPrix(1.5);
		pizza1.getIngredients().add(ingredient2);
		
		Pizza pizza2 = new Pizza();
		pizza2.setLibelle("Pizza 2");
		pizza2.setIngredients(new ArrayList<>());
		pizza2.getIngredients().add(ingredient1);
		
		pizzas.add(pizza1);
		pizzas.add(pizza2);
		panier.setPizzas(pizzas);
		
		final int PANIER_ID = 0;
		Mockito.when(panierDaoMock.getPanierById(PANIER_ID)).thenReturn(panier);
		
		// when
		Panier result = panierService.getPanierById(PANIER_ID);
		double prixPizza1 = result.getPizzas().get(0).getPrix();
		double prixPizza2 = result.getPizzas().get(1).getPrix();
		double prixPanier = result.getTotalPrix();
		
		// then
		Assertions.assertThat(result).isEqualTo(panier);
		Assertions.assertThat(prixPizza1)
			.isEqualTo(ingredient1.getPrix() + ingredient2.getPrix());
		Assertions.assertThat(prixPizza2).isEqualTo(ingredient1.getPrix());
		Assertions.assertThat(prixPanier).isEqualTo(prixPizza1 + prixPizza2);
	}

}
