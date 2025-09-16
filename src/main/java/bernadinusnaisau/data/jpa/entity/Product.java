package bernadinusnaisau.data.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Product.searchProductByName",
                query = "SELECT p FROM Product p WHERE p.name = :name"
        ),
        @NamedQuery(
                name = "Product.findProductById",
                query = "SELECT p FROM Product p WHERE p.id = :id"
        )
})

@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
}
