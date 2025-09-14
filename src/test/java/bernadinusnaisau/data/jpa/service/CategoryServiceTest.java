package bernadinusnaisau.data.jpa.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void validAddData() {
     Assertions.assertThrows(RuntimeException.class, () -> {
         categoryService.create();
     });
    }

    @Test
    void invalidAddData() {
        assertThrows(RuntimeException.class, () -> {
            categoryService.test();
        });
    }

    @Test
    void Programmatic() {
        assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory();
        });
    }

    @Test
    void ManualTransaction() {
        assertThrows(RuntimeException.class, () -> {
            categoryService.manualTransaction();
        });
    }
}
