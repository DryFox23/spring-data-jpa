package bernadinusnaisau.data.jpa.repository;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TransactionOperations transactionOperations;

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

//    @Test
//    void selectDataUsingPageable() {
//        //page 1
//        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("id")));
//        List<Product> products = productRepository.findAllByCategory_Name("GADGET PRICEY", pageable);
//        assertEquals(1, products.size());
//        assertEquals("Xiaomi Redmi Note 12", products.get(0).getName());
//
//        //page 2
//        pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.asc("id")));
//        products = productRepository.findAllByCategory_Name("GADGET PRICEY", pageable);
//        assertEquals(1, products.size());
//        assertEquals("Samsung Galaxy A14", products.get(0).getName());
//    }

    @Test
    void selectDataUsingPageableWithCountPage() {
//        Page 1
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("id")));
        Page<Product> products = productRepository.findAllByCategory_Name("GADGET PRICEY", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Xiaomi Redmi Note 12", products.getContent().get(0).getName());

//        Page 2
        pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.asc("id")));
        products = productRepository.findAllByCategory_Name("GADGET PRICEY", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(1, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Samsung Galaxy A14", products.getContent().get(0).getName());
    }

    @Test
    void countDataProduct() {
        Long count = productRepository.countByCategory_Name("GADGET PRICEY");
        assertEquals(2, count);
        assertEquals("Xiaomi Redmi Note 12", productRepository.findAllByCategory_Name("GADGET PRICEY").get(0).getName());
        assertEquals("Samsung Galaxy A14", productRepository.findAllByCategory_Name("GADGET PRICEY").get(1).getName());
    }

    @Test
    void isexsitsDataProduct() {
        boolean exists = productRepository.existsById(1L);
        assertTrue(exists);

        exists = productRepository.existsById(3L);
        assertFalse(exists);
    }

    @Test
    void deleteDataProduct() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(1L).orElse(null);
            assertNotNull(category);

            {
                Product product = new Product();
                product.setName("ROG PHONE 6");
                product.setPrice(15000000L);
                product.setCategory(category);
                productRepository.save(product);
            }

            {
                int deleteData = productRepository.deleteProductById(5L);
                assertEquals(1, deleteData);
            }
        });
    }
}
