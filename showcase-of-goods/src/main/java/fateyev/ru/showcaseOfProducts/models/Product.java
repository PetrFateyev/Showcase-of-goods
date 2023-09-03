package fateyev.ru.showcaseOfProducts.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "showcase_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_SHOWCASE_ID"))
    @JsonBackReference
    private Showcase showcase;

    @Column(name = "position", nullable = false)
    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2, max = 100, message = "Position should be between 2 and 100 characters")
    private String position;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Column(name = "type", nullable = false)
    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Type should be between 2 and 50 characters")
    private String type;

    @Column(name = "price", precision=10, scale=2, nullable = false)
    @Min(value = 0, message = "Price should be greater than 0")
    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

}
