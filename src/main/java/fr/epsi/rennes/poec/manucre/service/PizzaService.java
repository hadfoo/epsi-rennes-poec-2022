package fr.epsi.rennes.poec.manucre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epsi.rennes.poec.manucre.dao.PanierDAO;
import fr.epsi.rennes.poec.manucre.dao.PizzaDAO;
import fr.epsi.rennes.poec.manucre.domain.Pizza;
import fr.epsi.rennes.poec.manucre.exception.BusinessException;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaDAO pizzaDAO;
	
	@Autowired
	private PanierDAO panierDAO;
	

	public List<Pizza> getAllPizza(){
		List<Pizza> pizzas = pizzaDAO.getAllPizza();
		return pizzas;
	}
	
	public void createPizza(Pizza pizza) throws BusinessException {
		if(pizza.getLibelle() == null) {
			throw new BusinessException("pizza.libelle.null");
		}
		//prevention cyber
		if (!pizza.getLibelle().matches("[a-zA-Z0-9\\-]+$")) {
			throw new BusinessException ("pizza.libelle.invalide");
		}
		int pizzaId = pizzaDAO.createPizza(pizza.getLibelle());
		if (pizza.getIngredients() != null) {
			for (int i = 0; i<pizza.getIngredients().size();i++) {
				int ingredientId = pizza.getIngredients().get(i).getId();
				pizzaDAO.addIngredientToPizza(pizzaId,  ingredientId);
			}
		}
	}
	
	public void addIngredientToPizza(int pizzaId, int ingredientId) throws BusinessException {
		if(pizzaId < 0 || ingredientId < 0) {
			throw new BusinessException("pizza.ingredient.id.invalide");
		}
		pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
	}
	
	public Pizza getPizzaById (int pizzaId) {
		return panierDAO.getPizzaById(pizzaId);
	}
	
	@Transactional
	public int supprimerPizza(int pizzaId) {
		pizzaDAO.supprimerPizza(pizzaId);
		return pizzaId;
	}
	
}
