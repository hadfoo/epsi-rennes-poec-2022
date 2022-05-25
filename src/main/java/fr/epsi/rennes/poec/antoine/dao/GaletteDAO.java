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
public class GaletteDAO {

	@Autowired
	private DataSource ds;
	
	public List<Galette> getAllGalettes() {
		
		/*
		 * Retourne la liste de toutes les galettes présentes dans la base de données.
		 */
		
		String sql = "select galette.id as galetteId, galette.libelle as galetteLibelle, "
				+ "group_concat(ingredient.libelle,':',ingredient.nbCalories,':',ingredient.prix) as ingredients "
				+ "from galette "
				+ "right join galette_has_ingredient on galette_has_ingredient.galetteId = galette.id " 
				+ "left join ingredient on galette_has_ingredient.ingredientId = ingredient.id " 
				+ "group by galette.id;";
		
		try (Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ResultSet rs = ps.executeQuery();
			List<Galette> galettes = new ArrayList<>();
			
			while (rs.next()) {
				Galette galette = new Galette();
				
				galette.setId(rs.getInt("galetteId"));
				galette.setLibelle(rs.getString("galetteLibelle"));
				
				String[] ingredientsArr = rs.getString("ingredients").split("\\,");
				List<Ingredient> ingredientsList = new ArrayList<>();
				
				for (String ingredientStr : ingredientsArr) {
					String[] ingredientStrArr = ingredientStr.split("\\:");
					String ingredientLibelle = ingredientStrArr[0];
					int ingredientNbCalories = Integer.parseInt(ingredientStrArr[1]);
					double ingredientPrix = Double.parseDouble(ingredientStrArr[2]);
					
					Ingredient ingredient = new Ingredient();
					ingredient.setLibelle(ingredientLibelle);
					ingredient.setNbcalories(ingredientNbCalories);
					ingredient.setPrix(ingredientPrix);
					
					ingredientsList.add(ingredient);
				}
				
				galette.setIngredients(ingredientsList);
				galettes.add(galette);
			}
			return galettes;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public Galette getGaletteById(int galetteId) {
		/*
		 * Retourne la galette identifiée par galetteId.
		 */
		
		String sql = "select galette.id as galetteId, galette.libelle as galetteLibelle, "
				+ "group_concat(ingredient.libelle,':',ingredient.nbCalories,':',ingredient.prix) as ingredients "
				+ "from galette "
				+ "right join galette_has_ingredient on galette_has_ingredient.galetteId = galette.id " 
				+ "left join ingredient on galette_has_ingredient.ingredientId = ingredient.id " 
				+ "where galette.id = ? "
				+ "group by galette.id;";
		
		try (Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, galetteId);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Galette galette = new Galette();
				
				galette.setId(rs.getInt("galetteId"));
				galette.setLibelle(rs.getString("galetteLibelle"));
				
				String[] ingredientsArr = rs.getString("ingredients").split("\\,");
				List<Ingredient> ingredientsList = new ArrayList<>();
				
				for (String ingredientStr : ingredientsArr) {
					String[] ingredientStrArr = ingredientStr.split("\\:");
					String ingredientLibelle = ingredientStrArr[0];
					int ingredientNbCalories = Integer.parseInt(ingredientStrArr[1]);
					double ingredientPrix = Double.parseDouble(ingredientStrArr[2]);
					
					Ingredient ingredient = new Ingredient();
					ingredient.setLibelle(ingredientLibelle);
					ingredient.setNbcalories(ingredientNbCalories);
					ingredient.setPrix(ingredientPrix);
					
					ingredientsList.add(ingredient);
				}
				
				galette.setIngredients(ingredientsList);
				return galette;
			}
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		throw new TechnicalException(new SQLException("Get galette by id error"));
	}
	
	public int createGalette(Galette galette) {
		/*
		 * Créer la galette en paramètre dans la base de données
		 */
		
		// 2 requêtes SQL : 1 pour créer la galette dans la table galette.galette.
		// l'autre pour ajouter les associations galette/ingrédient dans la table galette.galette_has_ingredient
		String sqlGalette = "insert into galette (libelle) values (?)";
		String sqlIngredient = "insert into galette_has_ingredient " +
				 "(galetteId, ingredientId) values (?, ?)" + 
				", (?, ?)".repeat(galette.getIngredients().size()-1) + ";";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement psGalette = conn.prepareStatement(sqlGalette, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psIngredient = conn.prepareStatement(sqlIngredient, Statement.RETURN_GENERATED_KEYS))
		    
		{
			// empêche les autocommits après chaque update des statements
			conn.setAutoCommit(false);
				
			psGalette.setString(1, galette.getLibelle());
			psGalette.executeUpdate();
				
			ResultSet rsGalette = psGalette.getGeneratedKeys();
			
			// si la galette a bien été ajouté à la table
			if (rsGalette.next()) {
				int galetteId = rsGalette.getInt(1);
				
				// ajoute les ingrédients 1 par 1 à la table
				for (int i=0; i < galette.getIngredients().size(); i++) {
					psIngredient.setInt(2*i+1, galetteId);
					psIngredient.setInt(2*(i+1), galette.getIngredients().get(i).getId());
				}
				psIngredient.executeUpdate();
				
				// on commit les 2 statements d'un coup
				conn.commit();
				
				return galetteId;
			}
			
			
	    } catch (SQLException e) {
			throw new TechnicalException(e);
		}
		
		throw new TechnicalException(new SQLException("Galette create error"));
	}
	
	public void addIngredientsToGalette(Galette galette, List<Ingredient> ingredients) {
		/*
		 * Ajoute la liste d'ingrédients en paramètre à la galette en paramètre.
		 */
		String sql = "insert into galette_has_ingredient " +
					 "(galetteId, ingredientId) values (?, ?)" + ", (?, ?)".repeat(ingredients.size()-1) + ";";
		try (
				Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i=0; i < ingredients.size(); i++) {
				ps.setInt(2*i+1, galette.getId());
				ps.setInt(2*(i+1), ingredients.get(i).getId());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void deleteGalette(Galette galette) {
		/*
		 * Supprimer la galette en paramètre de la table galette.galette.
		 * On n'a pas à se soucier (ni à craindre des erreurs) de supprimer de la table galette.galette_has_ingredient
		 * les associations galette/ingredient concernées, car on a ajouté à la table l'option DELETE ON CASCADE.
		 */
		
		String sql = "delete from galette where id = ?";
		
		try (Connection conn = ds.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, galette.getId());
			ps.executeUpdate();
			
		}  catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public Galette updateGalette(Galette newGalette) {
		/*
		 * On met à jour les attributs de la galette identifiée par l'id de newGalette dans la base.
		 */
		
		// on fait 3 requêtes :
		// 1. mettre à jour le libellé de la galette
		// 2. supprimer les ingrédients de la galette
		// 3. rajouter dans la galette les nouveaux ingrédients (qui peuvent être identiques à ceux de la galette
		//    avant la mise à jour.
		String sqlGalette = "update galette set galette.libelle = ? where galette.id = ?";
		String sqlDeleteIngredient = "delete from galette_has_ingredient where galetteId = ?";
		String sqlAddIngredient = "insert into galette_has_ingredient " +
				 "(galetteId, ingredientId) values (?, ?)" + ", (?, ?)".repeat(newGalette.getIngredients().size()-1) + ";";
		
		try (Connection conn = ds.getConnection();
				PreparedStatement psGalette = conn.prepareStatement(sqlGalette, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psDeleteIngredient = conn.prepareStatement(sqlDeleteIngredient, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psAddIngredient = conn.prepareStatement(sqlAddIngredient, Statement.RETURN_GENERATED_KEYS))
		    
		{
			// empêche les autocommits après chaque update des statements
			conn.setAutoCommit(false);
				
			psGalette.setString(1, newGalette.getLibelle());
			psGalette.setInt(2, newGalette.getId());
			psGalette.executeUpdate();
			
			psDeleteIngredient.setInt(1, newGalette.getId());
			psDeleteIngredient.executeUpdate();
				
			for (int i=0; i < newGalette.getIngredients().size(); i++) {
				psAddIngredient.setInt(2*i+1, newGalette.getId());
				psAddIngredient.setInt(2*(i+1), newGalette.getIngredients().get(i).getId());
			}
			psAddIngredient.executeUpdate();
			
			// commit les 3 statements
			conn.commit();
			
			return newGalette;
			
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
}



