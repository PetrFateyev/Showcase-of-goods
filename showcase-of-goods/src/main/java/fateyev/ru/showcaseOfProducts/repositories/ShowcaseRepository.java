package fateyev.ru.showcaseOfProducts.repositories;

import fateyev.ru.showcaseOfProducts.models.Showcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ShowcaseRepository extends JpaRepository<Showcase, UUID>, JpaSpecificationExecutor<Showcase> {

}
