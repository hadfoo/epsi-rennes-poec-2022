package fr.epsi.orel.dao;


import fr.epsi.orel.domain.Panier;
import fr.epsi.orel.domain.Pizza;
import fr.epsi.orel.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PanierDAO {

    @Autowired
    private DataSource ds;

    public void addPizza(Pizza pizza, int panierId) {

        String sql = "insert into panier_pizza " +
                "(panierid, pizzaid) values (?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, panierId);
            ps.setInt(2, pizza.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void delPizza(int panierId, int pizzaId) {
        String sql = "delete from panier_pizza " +
                "where panierid=? " +
                "and pizzaid=? " +
                "limit 1;";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, panierId);
            ps.setInt(2, pizzaId);

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
        String sql = "insert into panier (date) values (?)";
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

    public Panier getPanierById(int panierId) {
        String sql = "select " +
                "panier.id" +
                " as panierid, " +
                "panier.date as panierDate, " +
                "group_concat(pizza.id) as pizza " +
                "from panier " +
                "right join panier_pizza " +
                "on panier_pizza.panierid = panier.id " +
                "left join pizza " +
                "on panier_pizza.pizzaid = pizza.id " +
                "where panier.id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, panierId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Panier panier = new Panier();
                panier.setId(rs.getInt("panierId"));

                panier.setPizzas(new ArrayList<>());
                String pizzas = rs.getString("pizza");
                if (pizzas != null) {
                    for (String pizzaId : pizzas.split(",")) {
                        Pizza pizza = new Pizza();
                        pizza.setId(Integer.parseInt(pizzaId));

                        panier.getPizzas().add(pizza);
                    }
                    return panier;
                }
            }
            return null;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public List<Pizza> getPizzaLabelInPanier(int panierId) {
        String sql = "select panier.id as panierid, " +
                "group_concat(pizza.id, ':', pizza.label) as pizza " +
                "from panier " +
                "right join panier_pizza " +
                "on panier_pizza.panierid = panier.id " +
                "left join pizza on panier_pizza.pizzaid = pizza.id " +
                "where panierid = " + panierId;
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Pizza> pizzasInPanier = new ArrayList<>();
            rs.next();
            String pizzas = rs.getString("pizza");
            if (pizzas != null) {
                for (String pizzaLabel : pizzas.split(",")) {
                    String[] pizzaIdLabel = pizzaLabel.split(":");
                    int id = Integer.parseInt(pizzaIdLabel[0]);
                    String label = pizzaIdLabel[1];
                    Pizza pizza = new Pizza();
                    pizza.setId(id);
                    pizza.setLabel(label);
                    pizzasInPanier.add(pizza);
                }
                return pizzasInPanier;
            }
            return null;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

    }
}

