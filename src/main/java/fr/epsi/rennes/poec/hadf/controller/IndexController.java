package fr.epsi.rennes.poec.hadf.controller;

import java.util.List;

import fr.epsi.rennes.poec.hadf.exception.TechnicalException;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.epsi.rennes.poec.hadf.domain.Panier;
import fr.epsi.rennes.poec.hadf.domain.Pizza;
import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.service.PanierService;
import fr.epsi.rennes.poec.hadf.service.PizzaService;

@RestController
public class IndexController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private PanierService panierService;
	
	@GetMapping("/public/pizzas")
	public Response<List<Pizza>> getAllPizzas() {
		List<Pizza> pizzas = pizzaService.getAllPizzas();
		
		Response<List<Pizza>> response = new Response<>();
		response.setData(pizzas);
		
		return response;
	}
	
	@PostMapping("/public/panier/pizza")
	public Response<Integer> addPizza(
			@RequestParam int pizzaId,
			@RequestParam int panierId) {
		
		Pizza pizza = new Pizza();
		pizza.setId(pizzaId);
		
		panierId = panierService.addPizza(pizza, panierId);
		
		Response<Integer> response = new Response<>();
		response.setData(panierId);
		
		return response;
	}
	
	@GetMapping("/public/panier")
	public Response<Panier> getPanier(@RequestParam int panierId) {
		Panier panier = panierService.getPanierById(panierId);
		
		Response<Panier> response = new Response<>();
		response.setData(panier);
		
		return response;
	}

	@GetMapping("/public/pizza")
	public Response<Pizza> getPizza(@RequestParam int pizzaId){
		Pizza pizza = pizzaService.getPizzaById(pizzaId);

		Response<Pizza> response = new Response<>();
		response.setData(pizza);

		return response;
	}

	@PostMapping("/public/pizza/remove")
	public Response<Integer> removePizza(@RequestParam int pizzaId, @RequestParam int panierId){

		try {
			panierId = panierService.removePizza(pizzaId, panierId);

			Response<Integer> response = new Response<>();

			response.setSuccess(true);
			response.setData(panierId);
			return response;

		} catch (TechnicalException e) {
			throw new RuntimeException(e);
		}
	}

//	@PostMapping("/public/pizza/remove")
//	public Response<Void> removePizza(@RequestParam int pizzaId, @RequestParam int panierId){
//
//		try {
//			panierService.removePizza(pizzaId, panierId);
//
//			Response<Void> response = new Response<>();
//
//			response.setSuccess(true);
//			return response;
//
//		} catch (TechnicalException e) {
//			throw new RuntimeException(e);
//		}
//	}

}
