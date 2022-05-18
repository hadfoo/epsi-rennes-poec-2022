package fr.epsi.rennes.poec.hadf.dao;

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

import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.domain.Pizza;
import fr.epsi.rennes.poec.hadf.exception.TechnicalException;

@Repository
public class PizzaDAO {
	
	@Autowired
	private DataSource ds;
	
	public int createPizza(String libelle) {
		String sql = "insert into pizza (libelle) values (?)";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						sql, Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setString(1, libelle);
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Pizza create error"));
	}
	
	public void addIngredientToPizza(int pizzaId, int ingredientId) {
		String sql = "insert into pizza_ingredient " +
					 "(pizza_id, ingredient_id) values (?, ?)";
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
	
	public List<Pizza> getAllPizzas() {
		String sql = "select " +
						"pizza.id as pizzaId, " +
						"pizza.libelle as pizzaLibelle, " +
						"array_agg(ingredient.id) as ingredients " +
					 "from pizza " +
					 "right join pizza_ingredient " +
					 	"on pizza_ingredient.pizza_id = pizza.id " +
					 "left join ingredient " +
					 	"on pizza_ingredient.ingredient_id = ingredient.id " +
					 "group by pizza.id;";

		try (
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ResultSet rs = ps.executeQuery();

			List<Pizza> pizzas = new ArrayList<>();
			while (rs.next()) {
				Pizza pizza = new Pizza();
				String ingredients = rs.getString("ingredients");
				ingredients = ingredients.substring(1, ingredients.length() - 2);
				
				List<Ingredient> ingredientsList = new ArrayList<>();
				for (String ingredient : ingredients.split(",")) {
					Ingredient ingredientPojo = new Ingredient();
					ingredientPojo.setId(Integer.parseInt(ingredient));
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

}
