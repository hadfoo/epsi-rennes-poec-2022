package fr.epsi.rennes.poec.antoine.domain;

public class Ingredient {
	private int id;
	private String libelle;
	private int nbCalories;
	private double prix;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String label) {
		this.libelle = label;
	}
	public int getNbCalories() {
		return nbCalories;
	}
	public void setNbcalories(int nbCalories) {
		this.nbCalories = nbCalories;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

