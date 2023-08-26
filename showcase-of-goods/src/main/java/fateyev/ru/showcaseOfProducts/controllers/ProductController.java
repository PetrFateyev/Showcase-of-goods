package fateyev.ru.showcaseOfProducts.controllers;

import fateyev.ru.showcaseOfProducts.models.Product;
import fateyev.ru.showcaseOfProducts.services.ProductService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("showcases/{id}/products")
    public Page<Product> findAll(
            @Join(path = "showcases", alias = "s")
            @And({
                @Spec(path = "type", spec = Equal.class),
                @Spec(
                        path = "price",
                        params = {"min", "max"},
                        spec = Between.class
                )
    }) Specification<Product> productSpecificationpecification, Pageable pageable, @PathVariable(value = "id")UUID showcaseId){
        return productService.findAll(productSpecificationpecification, pageable, showcaseId);
    }

    @PostMapping("showcases/{id}/products")
    public ResponseEntity<HttpStatus> create(@PathVariable("id") UUID id, @RequestBody @Valid Product product){
        productService.save(id, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("showcases/{id}/products/{productId}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id,
                                             @PathVariable("productId") UUID productId, @RequestBody @Valid Product product){
        productService.update(id, productId, product);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("showcases/{id}/products/{productId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("productId") UUID id){
        productService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
