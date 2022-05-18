package fr.epsi.rennes.poec.hadf.domain;

import java.util.List;

public class Panier {
	
	private int id;
	private String userEmail;
	private List<Pizza> pizzas;
	private int totalCalories;
	private double totalPrix;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	public int getTotalCalories() {
		return totalCalories;
	}
	public void setTotalCalories(int totalCalories) {
		this.totalCalories = totalCalories;
	}
	public double getTotalPrix() {
		return totalPrix;
	}
	public void setTotalPrix(double totalPrix) {
		this.totalPrix = totalPrix;
	}

}
