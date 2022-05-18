package fr.epsi.orel.dao;

import fr.epsi.orel.domain.Ingredient;
import fr.epsi.orel.domain.Pizza;
import fr.epsi.orel.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PizzaDAO {

    @Autowired
    private DataSource ds;

    private static final Logger logger = LogManager.getLogger(String.valueOf(PizzaDAO.class));


    public int createPizza(String label) {
        String sql = "insert into pizza (label) values (?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, label);
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


    public void addIngredientToPizza(int pizzaid, int ingredientid) {

        String sql = "insert into pizza_ingredients " +
                "(pizzaid, ingredientid) values (?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pizzaid);
            ps.setInt(2, ingredientid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public List<Pizza> getAllPizzas() {

        String sql = "select " +
                "pizza.id as pizzaid, " +
                "pizza.label as PizzaLabel, " +
                "group_concat(ingredients.id) as ingredients " +
                "from pizza " +
                "right join pizza_ingredients " +
                "on pizza_ingredients.pizzaid = pizza.id " +
                "left join ingredients " +
                "on pizza_ingredients.ingredientid = ingredients.id " +
                "group by pizza.id;";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Pizza> pizzas = new ArrayList<>();
            while (rs.next()) {
                Pizza pizza = new Pizza();
                String ingredients = rs.getString("ingredients");

                List<Ingredient> ingredientsList = new ArrayList<>();
                for (String ingredient : ingredients.split(",")) {
                    Ingredient ingredientPojo = new Ingredient();
                    ingredientPojo.setId(Integer.parseInt(ingredient));
                    ingredientsList.add(ingredientPojo);
                }
                pizza.setIngredients(ingredientsList);
                pizza.setId(rs.getInt("pizzaId"));
                pizza.setLabel(rs.getString("pizzaLabel"));
                pizza.setPrix(getPrixPizza(pizza.getId()));
                pizzas.add(pizza);
            }
            return pizzas;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public double getPrixPizza(int pizzaid) {
        String sql = "select " +
                "ingredients.prix " +
                "from ingredients " +
                "right join pizza_ingredients " +
                "on pizza_ingredients.ingredientid = ingredients.id " +
                "left join pizza " +
                "on pizza_ingredients.pizzaid = pizza.id " +
                "where pizzaid = ?;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pizzaid);
            ResultSet rs = ps.executeQuery();
            double prixPizza = 0;
            while (rs.next()) {
                prixPizza += rs.getDouble(1);
            }
            return prixPizza;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}