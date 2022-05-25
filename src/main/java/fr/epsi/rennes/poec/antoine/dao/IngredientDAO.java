package fr.epsi.rennes.poec.antoine.dao;

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

import fr.epsi.rennes.poec.antoine.domain.Galette;
import fr.epsi.rennes.poec.antoine.domain.Ingredient;
import fr.epsi.rennes.poec.antoine.exception.TechnicalException;

@Repository
public class IngredientDAO {
	
	@Autowired
	private DataSource ds;
	
	public List<Ingredient> getAllIngredients() {
		String sql = "select id, libelle, nbCalories, prix from ingredient";
		
		try (Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ResultSet rs = ps.executeQuery();
			List<Ingredient> ingredients = new ArrayList<>();
			
			while (rs.next()) {
				Ingredient ingredient = new Ingredient();
				
				ingredient.setId(rs.getInt("id"));
				ingredient.setLibelle(rs.getString("libelle"));
				ingredient.setNbcalories(rs.getInt("nbCalories"));
				ingredient.setPrix(rs.getDouble("prix"));
				
				ingredients.add(ingredient);
			}
			return ingredients;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public int deleteIngredientFromGalette(Galette galette, Ingredient ingredient) {
		String sql = "delete from galette_has_ingredient where galetteId = ? and ingredientId = ?";
		try (Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			ps.setInt(1, galette.getId());
			ps.setInt(2, ingredient.getId());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		}  catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Delete ingredient from galette error"));
	}

}
