package fr.epsi.rennes.poec.manucre.domain;

import java.util.List;
import java.util.Objects;

public class Pizza {
	private int id;
	private String libelle;
	private int nbcalories;
	private double prix;
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
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pizza other = (Pizza) obj;
		return id == other.id;
	}
	
	
}
