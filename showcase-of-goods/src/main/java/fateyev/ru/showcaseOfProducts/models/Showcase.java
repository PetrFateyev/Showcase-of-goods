package fateyev.ru.showcaseOfProducts.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "showcases")
public class Showcase {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "showecase_id")
    private UUID showcaseId;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Column(name = "address", nullable = false)
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2, max = 250, message = "Address should be between 2 and 250 characters")
    private String address;

    @Column(name = "type", nullable = false)
    @NotEmpty(message = "Type should not be empty")
    @Size(min = 2, max = 50, message = "Type should be between 2 and 50 characters")
    private String type;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;

    @OneToMany(mappedBy = "showcase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> products;
}
