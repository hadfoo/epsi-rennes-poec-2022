package fr.epsi.rennes.poec.hadf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epsi.rennes.poec.hadf.dao.PanierDAO;
import fr.epsi.rennes.poec.hadf.domain.Panier;
import fr.epsi.rennes.poec.hadf.domain.Pizza;

@Service
public class PanierService {
	
	@Autowired
	private PanierDAO panierDAO;
	
	public int addPizza(Pizza pizza, int panierId) {
		boolean exists = panierDAO.isPanierExists(panierId);
		if (!exists) {
			panierId = panierDAO.createPanier();
		}
		panierDAO.addPizza(pizza, panierId);
		return panierId;
	}

	public Panier getPanierById(int panierId) {
		Panier panier = panierDAO.getPanierById(panierId);
		List<Pizza> pizzas = panier.getPizzas();
		double prixTotal = 0;
		for (int i = 0; i < pizzas.size(); i++) {
			double prixPizza = 0;
			Pizza pizza = pizzas.get(i);
			if (pizza.getIngredients() == null) {
				continue;
			}
			for (int j = 0; j < pizza.getIngredients().size(); j++) {
				prixPizza += pizza.getIngredients().get(j).getPrix();
			}
			pizza.setPrix(prixPizza);
			prixTotal += prixPizza;
		}
		panier.setTotalPrix(prixTotal);
		return panier;
	}
	
	@Transactional
	public void removePizza(Pizza pizza, int panierId) {
		panierDAO.removePizza(panierId, pizza.getId());
		for (int i = 0; i < pizza.getIngredients().size(); i++) {
			// supprimer les ingredients
			// ce qui revient à supprimer des lignes dans la table d'association
		}
	}

}
