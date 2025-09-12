package bernadinusnaisau.data.jpa.service;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void create(){
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("Category : " + i);
            categoryRepository.save(category);
        }
        throw new RuntimeException("Rollback Transaction");
    }

    public void test(){
        create();
    }
}
