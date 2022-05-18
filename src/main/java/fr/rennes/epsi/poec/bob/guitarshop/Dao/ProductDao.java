package fr.rennes.epsi.poec.bob.guitarshop.Dao;

import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;
import fr.rennes.epsi.poec.bob.guitarshop.Exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    private static final Logger logger = LogManager.getLogger(ProductDao.class);

    @Autowired
    private DataSource data_source;

    public DataSource getDs() {
        return this.data_source;
    }

    public List<Product> getAllProduct() throws SQLException {
        logger.info("##########ProductDao getAllProduct()");

        List<Product> products = new ArrayList<>();
        String sql = "SELECT brand, model, price FROM product";
        try {
            Connection conn = data_source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product current_product = new Product();
                current_product.setBrand(rs.getString(1));
                current_product.setModel(rs.getString(2));
                current_product.setPrice(rs.getInt(3));
                products.add(current_product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getAllProductGrep(String filter, String filter_value) throws SQLException {
        logger.info("##########ProductDao getAllProductGrep()");

        List<Product> products = new ArrayList<>();
        String sql = "SELECT brand, model, price FROM product WHERE ? = ?";

        try {
            Connection conn = data_source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, filter);
            ps.setString(2, filter_value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product current_product = new Product();
                current_product.setBrand(rs.getString(1));
                current_product.setModel(rs.getString(2));
                current_product.setPrice(rs.getInt(3));
                products.add(current_product);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return products;
    }

    public void createProduct(Product new_product) throws SQLException{
        logger.info("##########ProductDao createProduct()");


        List<Product> products = new ArrayList<>();
        String sql = "INSERT INTO product (brand, model, price) values (?, ?, ?)";

        try (
                Connection conn = data_source.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql))
                {
                        ps.setString(1, new_product.getBrand());
                        ps.setString(2, new_product.getModel());
                        ps.setInt(3, new_product.getPrice());
                        ps.executeUpdate();
                }
        catch (SQLException e)
        {
            throw new TechnicalException(e);
        }
    }

    public List<String> getAllCategory() throws SQLException {
        logger.info("##########ProductDao getAllProduct()");

        List<String> category = new ArrayList<>();
        String sql = "SELECT name FROM product_category";
        try {
            Connection conn = data_source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String current_category = (rs.getString(1));
                category.add(current_category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
