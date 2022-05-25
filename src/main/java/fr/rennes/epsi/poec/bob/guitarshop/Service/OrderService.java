package fr.rennes.epsi.poec.bob.guitarshop.Service;

import fr.rennes.epsi.poec.bob.guitarshop.Dao.OrderDao;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Order;

import fr.rennes.epsi.poec.bob.guitarshop.Exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @Autowired
    private OrderDao dao;

    public List<Order> getAllOrders() throws SQLException {
        logger.info("##########ProductService getAllProduct()");

        try {
            return dao.getAllOrders();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public Order getLastOrder() throws SQLException {
        logger.info("##########ProductService getAllProductGrep()");

        try {
            return dao.getLastOrder();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void createOrder(Order order) throws SQLException {
        logger.info("##########ProductService createProduct()");
        try {
            dao.createOrder(order.getCustomer_name(), (int) order.getPrice(), order.getContent());
        } catch (SQLException e)
        {
            throw new TechnicalException(e);
        }
    }

}
