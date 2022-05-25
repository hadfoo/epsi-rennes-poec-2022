package fr.epsi.rennes.poec.manucre.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.manucre.domain.Ingredient;
import fr.epsi.rennes.poec.manucre.exception.TechnicalException;

@Repository
public class IngredientDAO {
	
	@Autowired
	private DataSource ds;
	
	public List<Ingredient> getAllIngredients(){
		/**
		 * Liste tous les ingrédient de la table ingredient. On récupère l'id, le libelle, le nombre de calories, le prix et le type.
		 */
		String sql = "select * from ingredient";
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			List<Ingredient> ingredients = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Ingredient ingredient = new Ingredient();
				ingredient.setId(rs.getInt("id"));
				ingredient.setLibelle(rs.getString("libelle"));
				ingredient.setNbcalories(rs.getInt("nbcalories"));
				ingredient.setPrix(rs.getDouble("prix"));
				ingredient.setType(rs.getString("type"));
				ingredients.add(ingredient);
			}
			return ingredients;
		}catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
}
