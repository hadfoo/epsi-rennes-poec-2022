package fr.epsi.rennes.poec.manucre.domain;

public class Ingredient {
	private int id;
	private String type;
	private String libelle;
	private int nbcalories;
	private double prix;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String label) {
		this.libelle = label;
	}
	public int getNbcalories() {
		return nbcalories;
	}
	public void setNbcalories(int nbcalories) {
		this.nbcalories = nbcalories;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
