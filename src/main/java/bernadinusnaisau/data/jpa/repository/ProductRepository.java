package bernadinusnaisau.data.jpa.repository;

import bernadinusnaisau.data.jpa.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_Name(String name);
    Page<Product> findAllByCategory_Name(String name, Pageable pageable);
    List<Product> findByCategory_Id(Long id, Sort sort);

    Long countByCategory_Name(String name);
    boolean existsById(Long id);

    int deleteProductById(Long id);

    @Transactional
    int deleteProductByName(String name);

    List<Product>searchProductByName(@Param("name") String name);
    Product findProductById(@Param("id") Long id);
}
