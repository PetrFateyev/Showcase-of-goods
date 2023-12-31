package fateyev.ru.showcaseOfProducts.repositories;

import fateyev.ru.showcaseOfProducts.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByShowcaseShowcaseId(UUID showcaseId);
}
