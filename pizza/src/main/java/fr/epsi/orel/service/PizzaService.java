package fr.epsi.orel.service;

import fr.epsi.orel.dao.PizzaDAO;
import fr.epsi.orel.domain.Pizza;
import fr.epsi.orel.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaDAO pizzaDAO;

    public void createPizza(Pizza pizza) throws BusinessException {
        if (pizza.getLabel() == null) {
            throw new BusinessException("pizza.label.null");
        }
        int pizzaId = pizzaDAO.createPizza(pizza.getLabel());
        for (int i = 0; i < pizza.getIngredients().size(); i++) {
            int ingredientId = pizza.getIngredients().get(i).getId();
            pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
        }
    }

    public void addIngredientToPizza(int pizzaid, int ingredientid) throws BusinessException {
        if (pizzaid < 0 || ingredientid < 0) {
            throw new BusinessException("pizza.ingredient.id.invalid");
        }
        pizzaDAO.addIngredientToPizza(pizzaid, ingredientid);
    }

    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = pizzaDAO.getAllPizzas();


        return pizzas;
    }
}
