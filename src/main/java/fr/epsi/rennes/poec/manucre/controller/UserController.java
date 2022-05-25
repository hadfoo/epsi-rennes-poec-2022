package fr.epsi.rennes.poec.manucre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.manucre.domain.Pizza;
import fr.epsi.rennes.poec.manucre.domain.Response;
import fr.epsi.rennes.poec.manucre.domain.User;
import fr.epsi.rennes.poec.manucre.service.PizzaService;
import fr.epsi.rennes.poec.manucre.service.UserService;

@RestController
public class UserController {
	
	/*
	 * Class des controller du role user.
	 */
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PizzaService pizzaService;
	
	
	@PostMapping("/public/register")
	public void addUser (User user) {
		userService.addUser(user);
	}
	

	@GetMapping("/user/pizzas")
	public Response<List<Pizza>> getAllPizza(){
		List<Pizza> pizzas = pizzaService.getAllPizza();
		Response<List<Pizza>> response = new Response<>();
		response.setData(pizzas);
		return response;
	}

	
}
