package fr.epsi.rennes.poec.antoine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.antoine.dao.GaletteDAO;
import fr.epsi.rennes.poec.antoine.domain.Galette;
import fr.epsi.rennes.poec.antoine.domain.Ingredient;

@Service
public class GaletteService {
	
	@Autowired
	GaletteDAO galetteDAO;
	
	public List<Galette> getAllGalettes(){
		/*
		 * Retourne toutes les galettes de la carte.
		 */
		List<Galette> galettes = new ArrayList<Galette>();
		// récupère les galettes dans la base de données
		galettes = galetteDAO.getAllGalettes();
		
		// calcule et affecte le prix et le nombre de calories aux galettes
		for (Galette galette : galettes) {
			this.setPrix(galette);
			this.setNbCalories(galette);
		}
		return galettes;
	}

	public void setPrix(Galette galette) {
		/*
		 * Calcule le prix de la galette en paramètre (= somme du prix des ingrédients).
		 */
		double prix = 0d;
		
		for (Ingredient ingredient : galette.getIngredients()) {
			prix += ingredient.getPrix();
		}
		// arrondit pour avoir une valeur à 2 décimales.
		prix = Math.round(prix * 100.0) / 100.0;
		galette.setPrix(prix);
	}
	
	
	public void setNbCalories(Galette galette) {
		/*
		 * Calcule le nombre de calories de la galette en paramètre 
		 * (= somme du nombre de calories des ingrédients) 
		 */
		int nbCalories = 0;
		
		for (Ingredient ingredient : galette.getIngredients()) {
			nbCalories += ingredient.getNbCalories();
		}
		galette.setNbCalories(nbCalories);
	}
	
	public void deleteGalette(Galette galette) {
		/*
		 * supprime la galette en paramètre de la base de données
		 */
		this.galetteDAO.deleteGalette(galette);
	}
	
	public void createGalette(Galette galette) {
		/*
		 * crée la galette en paramètre dans la base de données
		 * et calcule son nombre de calories et son prix.
		 */
		this.galetteDAO.createGalette(galette);
		this.setNbCalories(galette);
		this.setPrix(galette);
	}
	
	public Galette getGaletteById(int galetteId) {
		/*
		 * récupère la galette identifiée par galetteId
		 */
		Galette galette = this.galetteDAO.getGaletteById(galetteId);
		this.setNbCalories(galette);
		this.setPrix(galette);
		return galette;
	}
	
	public Galette updateGalette(Galette newGalette) {
		/*
		 * Met à jour les attributs de la galette (désormais égaux à ceux de updatedGalette),
		 * identifiée par l'id de updatedGalette.
		 * Calcule son prix et son nombre de calories.
		 */
		Galette updatedGalette = this.galetteDAO.updateGalette(newGalette);
		this.setNbCalories(updatedGalette);
		this.setPrix(updatedGalette);
		return updatedGalette;
	}
}
