package fr.epsi.rennes.poec.antoine.domain;

import java.util.List;

public class Galette {
	
	private int id;
	private String libelle;
	private double prix;
	private int nbCalories;
	private List<Ingredient> ingredients; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getNbCalories() {
		return nbCalories;
	}
	public void setNbCalories(int nbCalories) {
		this.nbCalories = nbCalories;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
}
