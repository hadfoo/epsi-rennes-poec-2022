package fr.epsi.rennes.poec.gweltaz.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.gweltaz.domain.Ingredient;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import fr.epsi.rennes.poec.gweltaz.exception.TechnicalException;

@Repository
public class PizzaDAO {
	
	@Autowired
	private DataSource ds;
	
	public int createPizza (String label) {
		String sql = "insert into pizza (label) values (?)";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, label);		
				ps.executeUpdate();
				
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next()) {
					return rs.getInt(1);
				}
				throw new TechnicalException(new SQLException("Pizza Create Error"));
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
	}
	
	
	public void addIngredientToPizza (int pizzaId, int ingredientId) {
		
		String sql = "insert into pizza_ingredient"
				+ " (pizza_id, ingredient_id) values (?,?)";
		try (Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, pizzaId);
			ps.setInt(2, ingredientId);
			ps.executeUpdate();
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
	}
	
	
	
	public List<Pizza> getAllPizzas(){
		String sql = "SELECT p.id as pizzaId, p.label as pizzaLabel, " +
				"GROUP_CONCAT(ingredients.id, ':', ingredients.label," +
				" ':', ingredients.prix, ':', ingredients.nb_calories) as ingredients " +
				"FROM pizza as p " +
				"RIGHT JOIN pizza_ingredient ON pizza_ingredient.pizza_id = p.id " +
				"LEFT JOIN ingredients ON pizza_ingredient.ingredient_id = ingredients.id " +
				"GROUP BY p.label;";

		System.out.println(sql);

		List<Pizza> pizzas = new ArrayList<>();
		try (PreparedStatement stmt = ds.getConnection().prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Pizza pizza = new Pizza();
				String ingredients = rs.getString("ingredients");
				List<Ingredient> ingredientsList = new ArrayList<>();
				for (String ingredient : ingredients.split(",")) {
					String[] idAndIng = ingredient.split(":");
					Ingredient ingredientPojo = new Ingredient();
					ingredientPojo.setId(Integer.parseInt(idAndIng[0]));
					ingredientPojo.setLabel(idAndIng[1]);
					ingredientPojo.setPrix(Double.parseDouble(idAndIng[2]));
					ingredientPojo.setNbCalories(Integer.parseInt(idAndIng[3]));
					ingredientsList.add(ingredientPojo);
				}
				pizza.setIngredients(ingredientsList);
				pizza.setLabel(rs.getString("pizzaLabel"));
				pizza.setId(rs.getInt("pizzaId"));
				pizza.setPrice(pizza.getPrice());
				pizza.setCalories(pizza.getNbCalories());
				pizzas.add(pizza);
			}
			return pizzas;
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
}