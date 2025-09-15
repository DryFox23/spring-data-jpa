package bernadinusnaisau.data.jpa.repository;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void insertData() {
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        {
            Product product = new Product();
            product.setName("Xiaomi Redmi Note 12");
            product.setPrice(3000000L);
            product.setCategory(category);
            productRepository.save(product);
        }
        {
            Product product = new Product();
            product.setName("Samsung Galaxy A14");
            product.setPrice(2500000L);
            product.setCategory(category);
            productRepository.save(product);
        }
    }

    @Test
    void selectDataByProductName() {
        List<Product> products = productRepository.findAllByCategory_Name("GADGET PRICEY");
        assertNotNull(products);

        assertEquals(2, products.size());
        assertEquals("Xiaomi Redmi Note 12", products.get(0).getName());
        assertEquals("Samsung Galaxy A14", products.get(1).getName());
    }

    @Test
    void selectDataByProductId() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findByCategory_Id(1L, sort);
        assertNotNull(products);

        assertEquals(2, products.size());
        assertEquals(2L, products.get(0).getId());
        assertEquals(1L, products.get(1).getId());
    }
}
