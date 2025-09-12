package bernadinusnaisau.data.jpa;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addData() {
        Category category = new Category();
        category.setName("GADGET");
        categoryRepository.save(category);

        Assertions.assertNotNull(category.getId());
    }

    @Test
    void updateData() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        category.setName("GADGET PRICEY");
        categoryRepository.save(category);

        category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertEquals("GADGET PRICEY", category.getName());
    }
}
