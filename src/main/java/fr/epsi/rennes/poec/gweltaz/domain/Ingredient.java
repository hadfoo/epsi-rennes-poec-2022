package fr.epsi.rennes.poec.gweltaz.domain;

public class Ingredient {
	
	private int id;
	private String type;
	private String label;
	private double price;
	private int nbCalories;
	
	
	
	///// GETTERS AND SETTERS /////
	
	
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNbCalories() {
		return nbCalories;
	}
	public void setNbCalories(int nbCalories) {
		this.nbCalories = nbCalories;
	}


    public void setPrix(double parseDouble) {
		this.price=0;
    }
}
