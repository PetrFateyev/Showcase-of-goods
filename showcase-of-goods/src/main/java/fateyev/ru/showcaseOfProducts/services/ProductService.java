package fateyev.ru.showcaseOfProducts.services;

import fateyev.ru.showcaseOfProducts.models.Product;
import fateyev.ru.showcaseOfProducts.repositories.ProductRepository;
import fateyev.ru.showcaseOfProducts.repositories.ShowcaseRepository;
import fateyev.ru.showcaseOfProducts.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public Page<Product> findAll(Specification<Product> specification, Pageable pageable, UUID showcaseId) {
        if (!showcaseRepository.existsById(showcaseId)){
            throw new NotFoundException("Showcase with this id was not found");
        }
        return productRepository.findAll(specification, pageable);
    }

    @Transactional
    public void save(UUID showcaseId, Product product) {
        showcaseRepository.findById(showcaseId).map(showcase -> {
            product.setShowcase(showcase);
            return productRepository.save(product);
                }).orElseThrow(() -> new NotFoundException("Showcase with this id was not found"));
    }

    @Transactional
    public void update(UUID id, UUID productId, Product updatedProduct) {
        if (!productRepository.existsById(productId)){
            throw new NotFoundException("Product with this id was not found");
        }
        showcaseRepository.findById(id).map(showcase -> {
            updatedProduct.setProductId(productId);
            updatedProduct.setShowcase(showcase);
            return productRepository.save(updatedProduct);
        }).orElseThrow(() -> new NotFoundException("Showcase with this id was not found"));

    }

    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
