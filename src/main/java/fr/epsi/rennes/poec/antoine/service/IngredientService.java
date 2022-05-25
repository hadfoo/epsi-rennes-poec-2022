package fr.epsi.rennes.poec.antoine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.antoine.dao.IngredientDAO;
import fr.epsi.rennes.poec.antoine.domain.Galette;
import fr.epsi.rennes.poec.antoine.domain.Ingredient;

@Service
public class IngredientService {

	@Autowired
	private IngredientDAO ingredientDAO;
	
	public List<Ingredient> getAllIngredients(){
		/*
		 * Récupère la liste de tous les ingrédients dans la base de données.
		 */
		return this.ingredientDAO.getAllIngredients();
	}
	
	public void deleteIngredientFromGalette(Galette galette, Ingredient ingredient) {
		/*
		 * Retire l'ingrédient en paramètre de la galette en paramètre.
		 */
		ingredientDAO.deleteIngredientFromGalette(galette, ingredient);
	}
}
