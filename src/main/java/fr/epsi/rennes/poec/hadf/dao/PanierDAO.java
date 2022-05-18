package fr.epsi.rennes.poec.hadf.dao;

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

import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.domain.Panier;
import fr.epsi.rennes.poec.hadf.domain.Pizza;
import fr.epsi.rennes.poec.hadf.exception.TechnicalException;

@Repository
public class PanierDAO {
	
	@Autowired
	private DataSource ds;
	
	public void addPizza(Pizza pizza, int panierId) {
		String sql = "insert into panier_pizza " +
					 "(panier_id, pizza_id) values (?, ?)";
		
		try (Connection conn = ds.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, panierId);
			ps.setInt(2, pizza.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public boolean isPanierExists(int panierId) {
		String sql = "select id from panier where id = ?";
		try (Connection conn = ds.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, panierId);
			
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public int createPanier() {
		String sql = "insert into panier (date) values(?)";
		try (Connection conn = ds.getConnection();
			 PreparedStatement ps = conn.prepareStatement(
					 sql, Statement.RETURN_GENERATED_KEYS)) {
			
			String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
			ps.setString(1, date);
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Panier creation error"));
	}
	
	public Pizza getPizzaById(int pizzaId) {
		String sql = "select " +
						"pizza.id as id, " +
						"pizza.libelle as libelle, " +
						"array_agg((" +
							"ingredient.id, ':', ingredient.libelle, ':', ingredient.prix)) " +
						"as ingredients " +
					 "from pizza " +
					 "join pizza_ingredient " +
					 	"on pizza_ingredient.pizza_id = pizza.id " +
					 "join ingredient " +
					 	"on ingredient.id = pizza_ingredient.ingredient_id " +
					 "where id = '" + pizzaId + "'" +
					 "group by pizza.id";
	}
	
	public Panier getPanierById(int panierId) {
		String sql = "select " +
						"panier.id as panierId, " +
						"panier.date as panierDate, " +
						"array_agg(pizza.id) as pizzas " +
					 "from panier " +
					 "right join panier_pizza " +
					 	"on panier_pizza.panier_id = panier.id " +
					 "left join pizza " +
					 	"on panier_pizza.pizza_id = pizza.id " +
					 "where panier.id = ? " +
				 	 "group by panier.id";
		try (Connection conn = ds.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, panierId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Panier panier = new Panier();
				panier.setId(rs.getInt("panierId"));
				
				panier.setPizzas(new ArrayList<>());
				String pizzas = rs.getString("pizzas");
				if (pizzas == null || pizzas.length() == 0) {
					return panier;
				}
				pizzas = pizzas.substring(1, pizzas.length() - 2);
				
				for (String pizzaId : pizzas.split(",")) {
					if (pizzaId.length() == 0) {
						continue;
					}
					Pizza pizza = getPizzaById(Integer.parseInt(pizzaId));
					panier.getPizzas().add(pizza);
				}
				return panier;
			}
			return null;
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

}
