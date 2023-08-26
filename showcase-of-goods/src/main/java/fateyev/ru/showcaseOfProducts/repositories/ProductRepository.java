package fateyev.ru.showcaseOfProducts.repositories;

import fateyev.ru.showcaseOfProducts.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
