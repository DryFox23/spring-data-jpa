package bernadinusnaisau.data.jpa.repository;

import bernadinusnaisau.data.jpa.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addData() {
        Category category = new Category();
        category.setName("GADGET");
        categoryRepository.save(category);

        assertNotNull(category.getId());
    }

    @Test
    void updateData() {
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        category.setName("GADGET PRICEY");
        categoryRepository.save(category);

        category = categoryRepository.findById(1L).orElse(null);
        assertEquals("GADGET PRICEY", category.getName());
    }

    @Test
    void SelectQuery() {
        Category category = categoryRepository.findFirstByNameEquals("GADGET PRICEY").orElse(null);
        assertNotNull(category);
        assertEquals("GADGET PRICEY", category.getName());

        List<Category> categories = categoryRepository.findAllByNameLike("%GADGET%");
        assertFalse(categories.isEmpty());
        assertEquals("GADGET PRICEY", categories.get(0).getName());

        Category categoryById = categoryRepository.findById(1L).orElse(null);
        assertNotNull(categoryById);
        assertEquals("GADGET PRICEY", categoryById.getName());

    }

    @Test
    void testFindAllCategoriesWithSlice() {
        Pageable pageable = PageRequest.of(3, 3);

        Slice<Category> categorySlice = categoryRepository.findAll(pageable);
        Assertions.assertNotNull(categorySlice);

        while (categorySlice.hasNext()) {
            categorySlice = categoryRepository.findAll(categorySlice.nextPageable());
        }
    }

    @Test
    void auditing() {
        Category category = new Category();
        category.setName("GADGET MID RANGE");
        categoryRepository.save(category);

        assertNotNull(category.getId());
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getLastModifiedDate());
    }
}
