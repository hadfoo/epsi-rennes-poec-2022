package fr.epsi.rennes.poec.gweltaz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.sql.DataSource;

import fr.epsi.rennes.poec.gweltaz.domain.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.gweltaz.domain.Panier;
import fr.epsi.rennes.poec.gweltaz.domain.Pizza;
import fr.epsi.rennes.poec.gweltaz.exception.TechnicalException;

@Repository
public class PanierDAO {

	private static final Logger logger = LogManager.getLogger(PanierDAO.class);
	
	@Autowired
	private DataSource ds;
	
	public void addPizza(Pizza pizza, int panierId) {
		String sql = "insert into panier_pizza (panier_id, pizza_id) values (?,?)";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, panierId);
			ps.setInt(2, pizza.getId());
			ps.executeUpdate();
		}catch(SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	
	
	public boolean isPanierExists(int panierId) {
		String sql = "select id from panier where id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, panierId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		}catch(SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public int createPanier() {
		String sql = "insert into panier (timestamp) values (?)";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
			ps.setString(1, date);
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

	public Pizza getPizzaById(int pizzaId){
		String sql = "select " +
				"pizza.id as id, " +
				"pizza.label as label,  " +
				"group_concat(ingredients.id, ':', ingredients.label, ':', ingredients.prix) as ingredients " +
				"from pizza " +
				"join pizza_ingredient on pizza_ingredient.pizza_id = pizza.id " +
				"join ingredients " +
				"on ingredients.id = pizza_ingredient.ingredient_id " +
				"where pizza.id = ? " +
				"group by pizza.id";

		try(Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setInt(1, pizzaId);

			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Pizza pizza = new Pizza();
				pizza.setId(rs.getInt("id"));
				pizza.setLabel(rs.getString("label"));
				pizza.setIngredients(new ArrayList<>());

				String ingredientsString = rs.getString("ingredients");
				logger.debug("Liste des ingredients: {}", ingredientsString);
				if(ingredientsString != null && ingredientsString.length() > 0){
					String[] ingredientsTab = ingredientsString.split(",");
					for(String ingredient : ingredientsTab){
						String[] colonnes = ingredient.split("\\:");

						Ingredient ingredientPojo = new Ingredient();
						ingredientPojo.setId(Integer.parseInt(colonnes[0]));
						ingredientPojo.setLabel(colonnes[1]);
						ingredientPojo.setPrice(Double.parseDouble(colonnes[2]));

						pizza.getIngredients().add(ingredientPojo);
					}
				}
				return pizza;
			}
		}catch (SQLException e){
			throw new TechnicalException(e);
		}


		return null;
	}
	
	
	public Panier getPanierById(int panierId) {
		String sql = "select panier.id as panierId, panier.timestamp as panierDate, group_concat(pizza.id) as pizzas from panier "
				+ "right join panier_pizza on panier_pizza.panier_id = panier.id "
				+ "left join pizza on panier_pizza.pizza_id = pizza.id "
				+ "where panier.id = ? " +
				"group by panier.id";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, panierId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Panier panier = new Panier();
				panier.setId(rs.getInt("panierId"));
				
				panier.setPizzas(new ArrayList<>());
				String pizzas = rs.getString("pizzas");
				
				for(String pizzaId : pizzas.split(",")) {
					Pizza pizza = getPizzaById(Integer.parseInt(pizzaId));
					
					panier.getPizzas().add(pizza);
				}
				return panier;
			}
			return null;
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public void removePizza (int pizzaId, int panierId){
		String sql = "delete from panier " +
				"where id = ? ;";

		try(Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);){

			ps.setInt(1, panierId);
			ps.executeUpdate();

		}catch (SQLException e){
			throw new TechnicalException(e);
		}

	}

}
