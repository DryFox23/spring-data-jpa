package bernadinusnaisau.data.jpa.service;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional(propagation = Propagation.MANDATORY)
    public void create(){
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("Category : " + i);
            categoryRepository.save(category);
        }
        throw new RuntimeException("Rollback Transaction");
    }

    public void createCategory(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int i = 0; i <5; i++) {
                Category category = new Category();
                category.setName("Category : " + i);
                categoryRepository.save(category);
            }
            throwError();
        });
    }

    public void test(){
        create();
    }

    public void throwError(){
        throw new RuntimeException("Rollback Transaction");
    }

    public void manualTransaction(){
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setTimeout(10);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            for (int i = 0; i <5; i++) {
                Category category = new Category();
                category.setName("Category Manual: " + i);
                categoryRepository.save(category);
            }
            throwError();
            transactionManager.commit(status);
        }catch (Throwable throwable){
            transactionManager.rollback(status);
            throw throwable;
        }
    }
}
