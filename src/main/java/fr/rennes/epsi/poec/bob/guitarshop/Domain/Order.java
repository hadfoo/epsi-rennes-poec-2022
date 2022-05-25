package fr.rennes.epsi.poec.bob.guitarshop.Domain;

import java.util.List;

public class Order {

    private int id;

    private String customer_name;
    private List<Product> content;
    private double price;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public List<Product> getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public void setContent(List<Product> content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }
}
