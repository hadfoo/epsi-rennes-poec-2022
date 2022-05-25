package fr.epsi.rennes.poec.gweltaz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.gweltaz.dao.PizzaDAO;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import fr.epsi.rennes.poec.gweltaz.exception.BusinessException;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaDAO pizzaDAO;
	
	public List<Pizza> getAllPizzas(){
		List<Pizza> pizzas = pizzaDAO.getAllPizzas();
		return pizzas;
	}
	
	public void createPizza(Pizza pizza) throws BusinessException {
		if (pizza.getLabel() == null) {
			throw new BusinessException("Pizza.label.null");
		}
		int pizzaId = pizzaDAO.createPizza(pizza.getLabel());
		for(int i = 0; i < pizza.getIngredients().size(); i++) {
			int ingredientId = pizza.getIngredients().get(i).getId();
			pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
		}
	}
	
	public void addIngredientToPizza(int pizzaId, int ingredientId) throws BusinessException{
		if(pizzaId < 0 || ingredientId < 0) {
			throw new BusinessException("Pizza.ingredient.id.invalide");
		}
		pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
	}
	

}
