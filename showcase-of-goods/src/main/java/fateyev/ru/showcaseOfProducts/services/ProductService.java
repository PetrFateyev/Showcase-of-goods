package fateyev.ru.showcaseOfProducts.services;

import fateyev.ru.showcaseOfProducts.models.Product;
import fateyev.ru.showcaseOfProducts.repositories.ProductRepository;
import fateyev.ru.showcaseOfProducts.repositories.ShowcaseRepository;
import fateyev.ru.showcaseOfProducts.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ShowcaseRepository showcaseRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ShowcaseRepository showcaseRepository) {
        this.productRepository = productRepository;
        this.showcaseRepository = showcaseRepository;
    }

    public List<Product> findAll(UUID showcaseId) {
        if (!showcaseRepository.existsById(showcaseId)){
            throw new NotFoundException("Showcase with this id was not found");
        }
        return productRepository.findByShowcaseId(showcaseId);
    }

    @Transactional
    public void save(UUID showcaseId, Product product) {
        showcaseRepository.findById(showcaseId).map(showcase -> {
            product.setShowcase(showcase);
            return productRepository.save(product);
                }).orElseThrow(() -> new NotFoundException("Showcase with this id was not found"));
    }

    @Transactional
    public void update(UUID id, Product updatedProduct) {
        if (!productRepository.existsById(id)){
            throw new NotFoundException("Product with this id was not found");
        }
        updatedProduct.setProductId(id);
        productRepository.save(updatedProduct);
    }

    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
