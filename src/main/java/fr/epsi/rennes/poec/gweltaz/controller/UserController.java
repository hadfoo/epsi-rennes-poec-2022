package fr.epsi.rennes.poec.gweltaz.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import fr.epsi.rennes.poec.gweltaz.domain.Response;
import fr.epsi.rennes.poec.gweltaz.domain.User;
import fr.epsi.rennes.poec.gweltaz.service.PizzaService;
import fr.epsi.rennes.poec.gweltaz.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/public/register")
    public void addUser (User user) {
        this.userService.addUser(user);
    }
    
    @GetMapping("/user/pizzas")
    public Response<List<Pizza>> getAllPizzas(){
    	List<Pizza> pizzas = pizzaService.getAllPizzas();
    	Response<List<Pizza>> response = new Response<>();
    	response.setData(pizzas);
    	return response;
    }
}
