package fr.epsi.rennes.poec.manucre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.manucre.dao.IngredientDAO;
import fr.epsi.rennes.poec.manucre.domain.Ingredient;

@Service
public class IngredientService {
	@Autowired
	private IngredientDAO ingredientDAO;
	
	public List<Ingredient> getAllIngredients(){
		/*
		 * Service associé au dao getAllIngredients
		 */
		return ingredientDAO.getAllIngredients();
	}
}
