package fr.epsi.orel.controller;

import fr.epsi.orel.domain.Ingredient;
import fr.epsi.orel.domain.Pizza;
import fr.epsi.orel.domain.Response;
import fr.epsi.orel.exception.BusinessException;
import fr.epsi.orel.service.IngredientService;
import fr.epsi.orel.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/admin/ingredients")
    public Response<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        Response<List<Ingredient>> response = new Response<>();
        response.setData(ingredients);

        return response;
    }

    @PostMapping("/admin/pizza/create")
    public Response<Void> createPizza(@RequestBody Pizza pizza)
            throws BusinessException {

        pizzaService.createPizza(pizza);
        return new Response<>();
    }

    @PostMapping("/admin/pizza/ingredient")
    public Response<Void> addIngredientToPizza(
            @RequestParam int pizzaId,
            @RequestParam int ingredientId) throws BusinessException {

        pizzaService.addIngredientToPizza(pizzaId, ingredientId);
        return new Response<>();
    }

    @GetMapping("/admin/pizzas")
    public Response<List<Pizza>> getAllPizzas() {
        List<Pizza> pizzas = pizzaService.getAllPizzas();

        Response<List<Pizza>> response = new Response<>();
        response.setData(pizzas);

        return response;
    }

}
