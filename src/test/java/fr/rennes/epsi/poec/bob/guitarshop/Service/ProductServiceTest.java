package fr.rennes.epsi.poec.bob.guitarshop.Service;

import fr.rennes.epsi.poec.bob.guitarshop.Dao.ProductDao;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductDao productDaoMock;

    @Test
    public void getAllProductbyCategory() throws SQLException {
        // given
        Product product1 = new Product();
        product1.setBrand("Ibanez");
        product1.setModel("Troubadour");
        product1.setCategory("Guitar Amp");

        Product product2 = new Product();
        product2.setBrand("Ibanez");
        product2.setModel("Troubadour");
        product2.setCategory("Guitar Amp");

        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        // when
        final String CATEGORY = "Guitar Amp";
        Mockito.when(productDaoMock.getAllProductbyCategory(CATEGORY)).thenReturn(products);
        // when
        List<Product> result = productService.getAllProductbyCategory(CATEGORY);

        // then
        Assertions.assertThat(product1.getBrand())
                .isEqualTo("Ibanez");

//        Assertions.assertThat(order1.getPrice())
//                .isEqualTo(product1.getPrice() + product2.getPrice());
    }
}
