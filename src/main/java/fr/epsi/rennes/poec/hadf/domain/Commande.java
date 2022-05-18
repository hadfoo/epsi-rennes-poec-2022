package fr.epsi.rennes.poec.hadf.domain;

import java.util.List;

public class Commande {
	
	private String numero;
	private String clientEmail;
	private List<Pizza> pizzas;
	private double prixHT;
	private double prixTTC;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
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
	public double getPrixHT() {
		return prixHT;
	}
	public void setPrixHT(double prixHT) {
		this.prixHT = prixHT;
	}
	public double getPrixTTC() {
		return prixTTC;
	}
	public void setPrixTTC(double prixTTC) {
		this.prixTTC = prixTTC;
	}

}
