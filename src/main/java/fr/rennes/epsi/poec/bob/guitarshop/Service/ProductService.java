package fr.rennes.epsi.poec.bob.guitarshop.Service;

import fr.rennes.epsi.poec.bob.guitarshop.Dao.ProductDao;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;

import fr.rennes.epsi.poec.bob.guitarshop.Exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductDao dao;

    public List<Product> getAllProduct() throws SQLException {
        logger.info("##########ProductService getAllProduct()");


        try {
            return dao.getAllProduct();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<Product> getAllProductGrep(String filter, String filter_value) throws SQLException {
        logger.info("##########ProductService getAllProductGrep()");

        try {
            return dao.getAllProductGrep(filter, filter_value);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<Product> getAllProductbyCategory(String filter_category) throws SQLException {
        try {
            return dao.getAllProductbyCategory(filter_category);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void createProduct(Product new_product) {
        logger.info("##########ProductService createProduct()");
        try {
            dao.createProduct(new_product);
        } catch (SQLException e)
        {
            throw new TechnicalException(e);
        }
    }

    public List<String> getAllCategory() throws SQLException {
        logger.info("##########ProductService getAllCategory()");


        try {
            return dao.getAllCategory();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
