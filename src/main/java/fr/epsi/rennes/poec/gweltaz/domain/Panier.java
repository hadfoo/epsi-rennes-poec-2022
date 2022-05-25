package fr.epsi.rennes.poec.gweltaz.domain;

import java.util.List;

public class Panier {
	
	private int id;
	private String userEmail;
	private List<Pizza> pizzas;
	private double totalPrice;
	private int totaCalories;
	
	
	
	///// GETTERS AND SETTERS /////
	
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
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getTotaCalories() {
		return totaCalories;
	}
	public void setTotaCalories(int totaCalories) {
		this.totaCalories = totaCalories;
	}
	
	

}
