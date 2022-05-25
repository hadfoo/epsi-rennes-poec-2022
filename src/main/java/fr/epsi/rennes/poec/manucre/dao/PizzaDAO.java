package fr.epsi.rennes.poec.manucre.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.manucre.exception.TechnicalException;
import fr.epsi.rennes.poec.manucre.domain.Ingredient;
import fr.epsi.rennes.poec.manucre.domain.Pizza;

@Repository
public class PizzaDAO {

	@Autowired
	private DataSource ds;

	public int createPizza(String libelle) {
		/**
		 * Cree une pizza dans la table pizza
		 */
		String sql = "insert into pizza (libelle) values (?)";
		try (
				Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, libelle);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Pizza create error"));

	}

	public void addIngredientToPizza(int pizzaId, int ingredientId) {
		/**
		 * Ajoute des ingredient à une pizza dans la table pizzaingredient
		 */
		String sql = "insert into pizzaingredient " +
					"(pizzaId, ingredientId) values (?,?)";
		try (
				Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, pizzaId);
			ps.setInt(2, ingredientId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public List<Pizza> getAllPizza(){
		/**
		 * liste toute les pizza de la table pizza on récupère l'id et le libelle de chaque pizza,
		 * ainsi que les libelles de chaque ingredient contenu dans les pizza
		 */
		String sql = "select pizza.id as pizzaId, pizza.libelle as pizzaLibelle, group_concat(ingredient.libelle) as ingredients from pizza " +
					"right join pizzaingredient on pizzaingredient.pizzaId = pizza.id " +
					"left join ingredient on pizzaingredient.ingredientId = ingredient.id " +
					"group by pizza.id;";
		
		try (
				Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();
			List<Pizza> pizzas = new ArrayList<>();
			while(rs.next()) {
				Pizza pizza = new Pizza();
				String ingredients = rs.getString("ingredients");
				List<Ingredient> ingredientsList = new ArrayList<>();
				for(String ingredient : ingredients.split(",")) {
					Ingredient ingredientPojo = new Ingredient();
					ingredientPojo.setLibelle(ingredient);
					ingredientsList.add(ingredientPojo);
				}
				pizza.setIngredients(ingredientsList);
				pizza.setId(rs.getInt("pizzaId"));
				pizza.setLibelle(rs.getString("pizzaLibelle"));
				pizzas.add(pizza);
			}
			return pizzas;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void supprimerPizza (int pizzaId) {
		/**
		 * Supprime une pizza de la table pizza en fonction de son id,
		 * Supprime également toutes les association d'ingredient avec cet id, dans la table pizza ingredient.
		 */
		String sql = "delete from pizzaingredient where pizzaId = ?";
		String sql2 ="delete from pizza where id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				PreparedStatement ps2 = conn.prepareStatement(sql2)) {
			ps.setInt(1,  pizzaId);
			ps2.setInt(1,  pizzaId);
			ps.executeUpdate();	
			ps2.executeUpdate();
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
	}
	
	
	
}
