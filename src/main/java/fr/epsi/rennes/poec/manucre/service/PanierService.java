package fr.epsi.rennes.poec.manucre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epsi.rennes.poec.manucre.dao.PanierDAO;
import fr.epsi.rennes.poec.manucre.domain.Panier;
import fr.epsi.rennes.poec.manucre.domain.Pizza;
import fr.epsi.rennes.poec.manucre.domain.Ingredient;


@Service
public class PanierService {
	@Autowired
	private PanierDAO panierDAO;
	
	public int addPizza(Pizza pizza, int panierId) {
		boolean exists= panierDAO.isPanierExists(panierId);
		if(!exists) {
			panierId = panierDAO.createPanier();
		}
		panierDAO.addPizza(pizza, panierId);
		return panierId;
	}
	
	public Panier getPanierById(int panierId) {
		Panier panier = panierDAO.getPanierById(panierId);
		double totalPanier = 0;
		int totalCalories = 0;
		if (panier != null) {
			for (Pizza pizza : panier.getPizzas()) {
				double prixPizza = 0;
				int nbCalories = 0;
				for(Ingredient ingredient : pizza.getIngredients()) {
					prixPizza += ingredient.getPrix();
					nbCalories += ingredient.getNbcalories();
				}
				pizza.setPrix(prixPizza);
				pizza.setNbcalories(nbCalories);
				totalCalories += nbCalories;
				totalPanier += prixPizza;
			}
			panier.setTotal(totalPanier);
			panier.setTotalCalories(totalCalories);
		}
		return panier;
	}
	
	@Transactional
	public int suppPizza(Pizza pizza, int panierId) {
		panierDAO.suppPizza(pizza, panierId);
		return panierId;
	}
}

