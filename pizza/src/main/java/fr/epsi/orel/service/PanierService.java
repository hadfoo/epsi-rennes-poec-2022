package fr.epsi.orel.service;

import fr.epsi.orel.dao.PanierDAO;
import fr.epsi.orel.domain.Panier;
import fr.epsi.orel.domain.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void delPizza(int panierId, int pizzaId) {
        panierDAO.delPizza(panierId, pizzaId);
    }

    public Panier getPanierById(int panierId) {
        return panierDAO.getPanierById(panierId);
    }

    public List<Pizza> getPizzaLabelInPanier(int panierId) {
        return panierDAO.getPizzaLabelInPanier(panierId);
    }

    public double getPrixPanier(int panierId) {
        return panierDAO.getPrixPanier(panierId);
    }

    }
