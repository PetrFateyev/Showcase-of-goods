package fateyev.ru.showcaseOfProducts.controllers;

import fateyev.ru.showcaseOfProducts.dto.ProductDTO;
import fateyev.ru.showcaseOfProducts.models.Product;
import fateyev.ru.showcaseOfProducts.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name="Product", description="The Product API")
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Получение всех товаров витрины",
            description = "Позволяет получить все товары витрины с фильтрацией по типу и дипозону цен")
    @GetMapping("showcases/{id}/products")
    public List<ProductDTO> findAll(
            @Join(path = "showcases", alias = "s")
            @And({
                @Spec(path = "type", spec = Equal.class),
                @Spec(
                        path = "price",
                        params = {"min", "max"},
                        spec = Between.class
                )
    }) Specification<Product> productSpec, @PathVariable(value = "id")UUID showcaseId){
        return productService.findAll(productSpec, showcaseId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Operation(
            summary = "Добавление товара",
            description = "Позволяет добавить товар на витрину"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("showcases/{id}/products")
    public ProductDTO create(@PathVariable("id") UUID id, @RequestBody @Valid ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        productService.save(id, product);
        return convertToDTO(product);
    }

    @Operation(
            summary = "Изменение данных товара",
            description = "Позволяет изменить данные товара"
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("showcases/{id}/products/{productId}")
    public void update(@PathVariable("id") UUID id,
                                             @PathVariable("productId") UUID productId,
                                             @RequestBody @Valid ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        productService.update(id, productId, product);
    }

    @Operation(
            summary = "Удаление товара",
            description = "Позволяет удалить товар"
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("products/{productId}")
    public void delete(@PathVariable("productId") UUID id){
        productService.delete(id);
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
