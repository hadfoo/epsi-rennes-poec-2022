package fr.epsi.rennes.poec.gweltaz.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.gweltaz.domain.Ingredient;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import fr.epsi.rennes.poec.gweltaz.domain.Response;
import fr.epsi.rennes.poec.gweltaz.exception.BusinessException;
import fr.epsi.rennes.poec.gweltaz.service.IngredientService;
import fr.epsi.rennes.poec.gweltaz.service.PizzaService;

@RestController
public class AdminController {

	private static final Logger logger = LogManager.getLogger(AdminController.class);
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private IngredientService ingredientService; 
	
	@GetMapping("/admin/ingredients")
	public Response<List<Ingredient>> getAllIngredients(){
		List<Ingredient> ingredients = ingredientService.getAllIngredients();
		
		Response<List<Ingredient>> response = new Response<>();
		response.setData(ingredients);
		
		return response;
	}
	
	@PostMapping("/admin/pizza/create")
	public Response<Void> createPizza(@RequestBody Pizza pizza) throws BusinessException{
		logger.info("Création de pizza");
		pizzaService.createPizza(pizza);
		return new Response<>();
	}
	
	@PostMapping("/admin/pizza/ingredient")
	public Response<Void> addIngregientToPizza(
			@RequestParam int pizzaId,
			@RequestParam int ingredientId) throws BusinessException {
		pizzaService.addIngredientToPizza(pizzaId, ingredientId);
		return new Response<>();
	}
	
	
	@GetMapping("/admin/pizzas")
	public Response<List<Pizza>> getAllPizzas(){
		List<Pizza> pizzas = pizzaService.getAllPizzas();
		
		Response<List<Pizza>> response = new Response<>();
		response.setData(pizzas);
		
		return response;
	}

}
