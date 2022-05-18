package fr.rennes.epsi.poec.bob.guitarshop.Controller;

import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Response;
import fr.rennes.epsi.poec.bob.guitarshop.Service.ProductService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/GuitarShop")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;


    @GetMapping("/product-list")
    public List<Product> getAllProduct() throws SQLException {
        logger.info("##########ProductController getAllProduct()");
        return productService.getAllProduct();
    }

    @GetMapping("/productGrep")
    public List<Product> getAllProduct(String filter, String filter_value) throws SQLException {
        logger.info("##########ProductController getAllProductGrep()");

        List<Product> productListResult;

        if (filter != null && filter_value != null) {
            productListResult = productService.getAllProduct();
        } else {
            productListResult = productService.getAllProductGrep(filter, filter_value);
        }
        Response<List<Product>> response = new Response<>();
        response.setData(productListResult);

        return (List<Product>) response;
    }

    @GetMapping("/category")
    public List<String> getAllCategory() throws SQLException {
        logger.info("##########ProductController getAllCategory()");
        return productService.getAllCategory();
    }

    @PostMapping("/product/add")
    public void createProduct(@RequestParam String brand, String model, int price) {
        logger.info("##########ProductController createProduct()");

        Product new_product = new Product();
        new_product.setBrand(brand);
        new_product.setModel(model);
        new_product.setPrice(price);
        productService.createProduct(new_product);
    }
}
