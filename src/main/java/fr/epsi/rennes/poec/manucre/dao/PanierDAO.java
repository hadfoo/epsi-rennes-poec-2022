package fr.epsi.rennes.poec.manucre.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.manucre.domain.Ingredient;
import fr.epsi.rennes.poec.manucre.domain.Panier;
import fr.epsi.rennes.poec.manucre.domain.Pizza;
import fr.epsi.rennes.poec.manucre.exception.TechnicalException;

@Repository
public class PanierDAO {
	
	@Autowired
	private DataSource ds;
	
	public void addPizza(Pizza pizza, int panierId) {
		/**
		 * Ajoute une pizza dans le panier, cela crée une association panierId pizzaId dans la table panier_pizza
		 */
		String sql = "insert into panier_pizza (panierId, pizzaId) values (?,?)";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, panierId);
			ps.setInt(2, pizza.getId());
			ps.executeUpdate();
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void suppPizza (Pizza pizza, int panierId) {
		/**
		 * Supprime une pizza du panier, cela supprime une association pizzaId panierId dans la table panier_pizza
		 */
		String sql = "delete from panier_pizza where pizzaId = ? and panierId = ? limit 1";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1,  pizza.getId());
			ps.setInt(2,  panierId);
			ps.executeUpdate();
	
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public boolean isPanierExists(int panierId) {
		/**
		 * Verifie que le panier existe à partir de son id
		 */
		String sql = "select id from panier where id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1,  panierId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public int createPanier() {
		/**
		 * Cree un panier
		 */
		String sql = "insert into panier (date) values(?)";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
			ps.setString(1,  date);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Panier creation error"));
	}
	
	public Pizza getPizzaById (int pizzaId) {
		/**
		 * Recupere une pizza par son id, on récupère ici l'id et le libelle de la pizza,
		 * ainsi que la liste des ingredient associé contenant l'id, le libelle, le prix et le nombres de calories de l'ingredient.
		 */
		String sql = "select pizza.id as pizzaId, pizza.libelle as libelle, " + 
				"group_concat(ingredient.id, ':', ingredient.libelle, ':', ingredient.prix, ':', ingredient.nbcalories) as ingredientList from pizza " +
				"right join pizzaingredient on pizzaingredient.pizzaId = pizza.id " + 
				"left join ingredient on pizzaingredient.ingredientId = ingredient.id " +
				"where pizza.id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, pizzaId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Pizza pizza = new Pizza();
				pizza.setId(rs.getInt("pizzaId"));
				pizza.setLibelle(rs.getString("libelle"));
				pizza.setIngredients(new ArrayList<>());
				String ingredientList = rs.getString("ingredientList");
				if (ingredientList != null && ingredientList.length() > 0) {
					for (String ingredient : ingredientList.split(",")) {
						String[] elementIngredient = ingredient.split("\\:");
						Ingredient ingre = new Ingredient();
						ingre.setId(Integer.parseInt(elementIngredient[0]));
						ingre.setLibelle(elementIngredient[1]);
						ingre.setPrix(Double.parseDouble(elementIngredient[2]));
						ingre.setNbcalories(Integer.parseInt(elementIngredient[3]));
						pizza.getIngredients().add(ingre);
						
					}
					return pizza;
				}
			}
			return null;
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
	}
		
	
	
	public Panier getPanierById (int panierId) {
		/**
		 * On récupere le panier en fonction de son id.
		 */
		String sql = "select panier.id as panierId, panier.date as panierDate, group_concat(pizza.id) as pizzas from panier " +
				"right join panier_pizza on panier_pizza.panierId = panier.id " + 
				"left join pizza on panier_pizza.pizzaId = pizza.id " +
				"where panier.id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, panierId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Panier panier = new Panier();
				panier.setId(rs.getInt("panierId"));
				panier.setPizzas(new ArrayList<>());
				String pizzas = rs.getString("pizzas");
				if(pizzas != null) {
					for (String pizzaId : pizzas.split(",")) {
						Pizza pizza = getPizzaById(Integer.parseInt(pizzaId));
						panier.getPizzas().add(pizza);
						
					}
					return panier;
				}
			}
			return null;
			
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
	}
}

