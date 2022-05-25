package fr.rennes.epsi.poec.bob.guitarshop.Controller;


import fr.rennes.epsi.poec.bob.guitarshop.Domain.Order;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;
import fr.rennes.epsi.poec.bob.guitarshop.Service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/GuitarShop")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/all-orders")
    public List<Order> getAllOrders() throws SQLException {
        logger.info("##########ProductController getAllProduct()");
        return orderService.getAllOrders();
    }

    @GetMapping("/last-order")
    public Order getLastOrder() throws SQLException {
        logger.info("##########ProductController getAllProduct()");
        return orderService.getLastOrder();
    }

    @PostMapping("/add-order")
    public void createProduct(@RequestParam String customer_name) throws SQLException {
        logger.info("##########ProductController createProduct()");
        System.out.println("CONTROLLER AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Order new_order = new Order();
        new_order.setCustomer_name(customer_name);
        orderService.createOrder(new_order);
    }
}
