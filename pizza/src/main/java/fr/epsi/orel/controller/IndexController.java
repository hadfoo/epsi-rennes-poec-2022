package fr.epsi.orel.controller;

import fr.epsi.orel.domain.Panier;
import fr.epsi.orel.domain.Pizza;
import fr.epsi.orel.domain.Response;
import fr.epsi.orel.service.PanierService;
import fr.epsi.orel.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestParam int panierid,
            @RequestParam int pizzaid) {

        Pizza pizza = new Pizza();
        pizza.setId(pizzaid);
        pizza.setPrix(getPrixPizza(pizza.getId()));

        panierid = panierService.addPizza(pizza, panierid);

        Response<Integer> response = new Response<>();
        response.setData(panierid);

        return response;
    }

    private double getPrixPizza(int pizzaid) {
        return pizzaService.getPrixPizza(pizzaid);
    }

    @GetMapping("/public/prix_panier")
    public Response<Double> getPrixPanier(@RequestParam int panierId) {
        double prixPanier = panierService.getPrixPanier(panierId);
        Response<Double> response = new Response<>();
        response.setData(prixPanier);

        return response;
    }

    @PostMapping("/public/panier/pizza_del")
    public Response<Integer> delPizza(@RequestParam int panierId,
                                      @RequestParam int pizzaId) {

        panierService.delPizza(panierId, pizzaId);

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

    @GetMapping("/public/pizza_panier")
    public Response<List<Pizza>> getPizzaLabelInPanier(@RequestParam int panierId) {
        List<Pizza> pizza_panier = panierService.getPizzaLabelInPanier(panierId);

        Response<List<Pizza>> response = new Response<>();
        response.setData(pizza_panier);

        return response;
        //return panierService.getPizzaLabelInPanier(panierId);
    }
}
