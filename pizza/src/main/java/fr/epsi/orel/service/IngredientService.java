package fr.epsi.orel.service;

import fr.epsi.orel.dao.IngredientDAO;
import fr.epsi.orel.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }
}
