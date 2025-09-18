package bernadinusnaisau.data.jpa.repository;

import bernadinusnaisau.data.jpa.entity.Category;
import bernadinusnaisau.data.jpa.entity.Product;
import bernadinusnaisau.data.jpa.model.SimpleProduct;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByCategory_Name(String name);
    Page<Product> findAllByCategory_Name(String name, Pageable pageable);
    List<Product> findByCategory_Id(Long id, Sort sort);

    Long countByCategory_Name(String name);
    boolean existsById(Long id);

    int deleteProductById(Long id);

    @Transactional
    int deleteProductByName(String name);

    List<Product>searchProductByName(@Param("name") String name, Pageable pageable);
    Product findProductById(@Param("id") Long id);

    @Query(value = "select p from Product p where p.name like:name or p.category.name like:name ")
    List<Product>searchProductByName(@Param("name") String name);

    @Query(value = "select p from Product p where p.name like:name or p.category.name like:name")
    List<Product>searchProductByName2(@Param("name") String name, Pageable pageable);

    @Query(value = "select p from Product p where p.name like:name or p.category.name like:name",
    countQuery = "select count(p) from Product p where p.name like:name or p.category.name like:name")
    Page<Product>searchAndCountProductByName(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query(value = "delete from Product p where p.name = :name")
    int deleteProductByName2(@Param("name") String name);

    @Modifying
    @Query(value = "update Product p set p.price = 0 where p.id = :id")
    int updateProductByPrice(@Param("id") Long id);

    Stream<Product> streamAllByCategory(Category category);
    Slice<Product> findAllByCategory(Category category, Pageable pageable);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findFirstByIdEquals(Long id);

    List<SimpleProduct> findAllByNameLike(String name);
}
