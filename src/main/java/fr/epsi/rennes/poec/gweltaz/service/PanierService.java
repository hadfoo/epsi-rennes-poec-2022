package fr.epsi.rennes.poec.gweltaz.service;

import fr.epsi.rennes.poec.gweltaz.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.gweltaz.dao.PanierDAO;
import fr.epsi.rennes.poec.gweltaz.domain.Panier;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PanierService {
	@Autowired
	private PanierDAO panierDAO;
	
	public int addPizza(Pizza pizza, int panierId) {
		boolean exists = panierDAO.isPanierExists(panierId);
		if(!exists) {
			panierId = panierDAO.createPanier();
		}
		panierDAO.addPizza(pizza, panierId);
		return panierId;
	}
	
	public Panier getPanierById(int panierId) {
		Panier panier = panierDAO.getPanierById(panierId);
		List<Pizza> pizzas = panier.getPizzas();
		double prixTotal = 0;
		for(int i = 0; i < pizzas.size(); i++){
			double prixPizza = 0;
			Pizza pizza = pizzas.get(i);
			if (pizza.getIngredients()==null){
				continue;
			}
			for (Ingredient ingredient : pizza.getIngredients()) {
				double prixIngredient =  ingredient.getPrice();
				prixPizza += prixIngredient;
			}
			pizza.setPrice(prixPizza);
			prixTotal += prixPizza;
		}
		panier.setTotalPrice(prixTotal);
		return panier;
	}

//	@Transactional
//	public void removePizza(Pizza pizza, int panierId){
//		panierDAO.removePizza(panierId, pizza.getId());
//		boolean exists = panierDAO.isPanierExists(panierId);
//		for (int i = 0; i < pizza.getIngredients().size(); i++){
//
//		}
//		if(exists) {
//			for (pizza:panierDAO) {
//
//			}
//		}
//	}
}
