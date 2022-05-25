package fr.rennes.epsi.poec.bob.guitarshop.Dao;

import fr.rennes.epsi.poec.bob.guitarshop.Domain.Order;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {

    private static final Logger logger = LogManager.getLogger(OrderDao.class);

    private final DataSource data_source;

    @Autowired
    public OrderDao(DataSource data_source) {
        this.data_source = data_source;
    }

    public DataSource getDs() {
        return this.data_source;
    }

    public String orderMainQuery (){
        StringBuilder sql = new StringBuilder();

        sql.append("String sql = SELECT order_all.customer,");
        sql.append(" GROUP_CONCAT(CONCAT(p.brand, \":\", p.model)) as purchased, p.price ");
        sql.append("FROM order_has_product as ohp");
        sql.append("INNER JOIN product as p on ohp.id_product = p.id");
        sql.append("INNER JOIN order_all ON ohp.id_order = order_all.id");
        sql.append("GROUP BY order_all.customer;");

        return String.valueOf(sql);
    }

    public List<Order> getAllOrders() throws  SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = orderMainQuery();
        try {
            Connection conn = data_source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    };


    public Order getLastOrder() throws SQLException {
        logger.info("##########OrderDao getAllProduct()");
        Order order = new Order();
        String sql = orderMainQuery();
        sql = StringUtils.chop(sql);
        sql += "ORDER BY order_all.date ASC LIMIT 1;";

        try {
            Connection conn = data_source.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    private void createProductObjFromDb(List<Product> products, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product current_product = new Product();
            current_product.setId(rs.getInt(1));
            current_product.setBrand(rs.getString(2));
            current_product.setModel(rs.getString(3));
            current_product.setPrice(rs.getInt(4));
            products.add(current_product);
        }
    }

    private void createOrderObjFromDb(Order order, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Order current_order = new Order();
            current_order.setCustomer_name(rs.getString(1));
        //..TODO..//
        // FOREACH PRODUCT IN PRODUCT LIST
        // createProductObjFromDB()

        }
    }

    @Transactional
    public void createOrder(@RequestBody Order customorderer) throws SQLException{
        System.out.println("DAO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        StringBuilder sql = new StringBuilder();
        sql.append("SET autocommit: 0;");
        sql.append("START TRANSACTION");
        sql.append("SELECT @neworder := MAX(order_all.id) + 1 FROM order_all");
        StringBuilder insert_query = new StringBuilder();
        insert_query.append("INSERT INTO order_all (date, customer, price) values (NOW(), ?, ?)");
        List list_id = new ArrayList();
        for(int i = 0; i < list_id.size(); i++) {
            String temp_query = "INSERT INTO order_has_product ('id_order', 'id_product')";
            temp_query += "VALUES (@neworder, " + list_id.get(i) + ");";
            insert_query.append(temp_query);
        }
        sql.append(insert_query);
        sql.append("COMMIT; SET Autocommit = 1;");
        System.out.println(sql);
//        try (
//            Connection conn = data_source.getConnection();
//            PreparedStatement ps = conn.prepareStatement(String.valueOf(sql));
//            PreparedStatement statement = conn.prepareStatement(String.valueOf(sql), new int[]{Statement.RETURN_GENERATED_KEYS}));
//
//        {
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
