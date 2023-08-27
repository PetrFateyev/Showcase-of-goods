package fateyev.ru.showcaseOfProducts.controllers;

import fateyev.ru.showcaseOfProducts.models.Showcase;
import fateyev.ru.showcaseOfProducts.services.ShowcaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@Tag(name="Showcase", description="The Showcase API")
@RestController
@RequestMapping("/api")
public class ShowcaseController {

    private final ShowcaseService showcaseService;

    @Autowired
    public ShowcaseController(ShowcaseService showcaseService) {
        this.showcaseService = showcaseService;
    }

    @Operation(
            summary = "Получение всех витрин",
            description = "Позволяет получить все витрины с фильтрацией по типу, адрессу," +
                    "за период по дате создания и за период по дате последней актуализации")
    @GetMapping("/showcases")
    public  Page<Showcase> getAll(
            @And({
                    @Spec(path = "type", spec = Equal.class),
                    @Spec(path = "address", spec = Equal.class),
                    @Spec(
                            path = "createdDate",
                            params = {"afterCreation", "beforeCreation"},
                            spec = Between.class
                    ),
                    @Spec(
                            path = "modifiedDate",
                            params = {"afterModified", "beforeModified"},
                            spec = Between.class
                    )
            }) Specification<Showcase> showcaseSpecification, Pageable pageable){
        return showcaseService.findAll(showcaseSpecification, pageable);
    }

    @Operation(
            summary = "Добавление витрины",
            description = "Позволяет добавить витрину"
    )
    @PostMapping("/showcases")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Showcase showcase){
        showcaseService.save(showcase);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Изменение данных витрины",
            description = "Позволяет изменить данные витрины"
    )
    @PutMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id, @RequestBody @Valid Showcase showcase) {
        showcaseService.update(id, showcase);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление витрины",
            description = "Позволяет удалить витрину"
    )
    @DeleteMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id) {
        showcaseService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
