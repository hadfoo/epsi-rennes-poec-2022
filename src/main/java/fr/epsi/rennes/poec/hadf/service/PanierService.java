package fr.epsi.rennes.poec.hadf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return panierDAO.getPanierById(panierId);
	}

}
