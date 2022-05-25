package fr.epsi.rennes.poec.gweltaz.domain;

import java.util.List;

public class Order {
	
	private String number;
	private String clientEmail;
	private List<Pizza> pizzas;
	private double HTPrice;
	private double TTCPrice;



	///// GETTERS AND SETTERS /////

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	public double getHTPrice() {
		return HTPrice;
	}
	public void setHTPrice(double hTPrice) {
		HTPrice = hTPrice;
	}
	public double getTTCPrice() {
		return TTCPrice;
	}
	public void setTTCPrice(double tTCPrice) {
		TTCPrice = tTCPrice;
	}
	
	
	

}
