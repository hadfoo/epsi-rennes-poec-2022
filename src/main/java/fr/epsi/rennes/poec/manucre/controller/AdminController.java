package fr.epsi.rennes.poec.manucre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.manucre.domain.Ingredient;
import fr.epsi.rennes.poec.manucre.domain.Pizza;
import fr.epsi.rennes.poec.manucre.domain.Response;
import fr.epsi.rennes.poec.manucre.exception.BusinessException;
import fr.epsi.rennes.poec.manucre.service.IngredientService;
import fr.epsi.rennes.poec.manucre.service.PizzaService;

@RestController
public class AdminController {
	/*
	 * Class des controller du role admin.
	 */

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
		pizzaService.createPizza(pizza);
		return new Response<>();
	}
	
	@PostMapping("/admin/pizza/ingredient")
	public Response<Void> addIngredientToPizza(@RequestParam int pizzaId, @RequestParam int ingredientId) throws BusinessException{
		pizzaService.addIngredientToPizza(pizzaId, ingredientId);
		return new Response<>();
	}
	
	@GetMapping("/admin/pizzas")
	public Response<List<Pizza>> getAllPizzas(){
		List<Pizza> pizzas = pizzaService.getAllPizza();
		Response<List<Pizza>> response = new Response<>();
		response.setData(pizzas);
		return response;
	}
	
	@PostMapping("/admin/pizza/supprimerPizza")
	public Response<Integer> supprimerPizza(@RequestParam int pizzaId){
		pizzaService.supprimerPizza(pizzaId);
		Response<Integer> response = new Response<>();
		response.setData(pizzaId);
		return response;
	}
}
